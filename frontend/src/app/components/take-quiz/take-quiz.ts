import { Component, OnInit, OnDestroy, signal, computed } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { QuizService } from '../../services/quiz.service';
import { QuizDetail, SubmitQuizRequest } from '../../models/quiz.model';

@Component({
  selector: 'app-take-quiz',
  imports: [FormsModule],
  templateUrl: './take-quiz.html',
  styleUrl: './take-quiz.scss',
})
export class TakeQuiz implements OnInit, OnDestroy {
  quiz = signal<QuizDetail | null>(null);
  loading = signal(true);
  submitting = signal(false);
  currentIndex = signal(0);
  playerName = signal('');
  started = signal(false);
  answers = signal<Map<number, Set<number>>>(new Map());
  timeLeft = signal(0);
  private timerInterval: ReturnType<typeof setInterval> | null = null;
  private startTime = 0;

  currentQuestion = computed(() => {
    const q = this.quiz();
    if (!q) return null;
    return q.questions[this.currentIndex()];
  });

  progress = computed(() => {
    const q = this.quiz();
    if (!q || q.questions.length === 0) return 0;
    return ((this.currentIndex() + 1) / q.questions.length) * 100;
  });

  answeredCount = computed(() => {
    return this.answers().size;
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private quizService: QuizService,
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.quizService.getQuiz(id).subscribe({
      next: (quiz) => {
        this.quiz.set(quiz);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  ngOnDestroy() {
    this.stopTimer();
  }

  startQuiz() {
    if (!this.playerName().trim()) return;
    this.started.set(true);
    this.startTime = Date.now();
    const q = this.quiz();
    if (q?.timeLimitMinutes) {
      this.timeLeft.set(q.timeLimitMinutes * 60);
      this.timerInterval = setInterval(() => {
        const newTime = this.timeLeft() - 1;
        this.timeLeft.set(newTime);
        if (newTime <= 0) {
          this.submitQuiz();
        }
      }, 1000);
    }
  }

  stopTimer() {
    if (this.timerInterval) {
      clearInterval(this.timerInterval);
      this.timerInterval = null;
    }
  }

  formatTime(seconds: number): string {
    const m = Math.floor(seconds / 60);
    const s = seconds % 60;
    return `${m}:${s.toString().padStart(2, '0')}`;
  }

  selectOption(questionId: number, optionId: number, type: string) {
    const current = new Map(this.answers());
    if (type === 'MULTIPLE_CHOICE') {
      const existing = current.get(questionId) ?? new Set<number>();
      const updated = new Set(existing);
      if (updated.has(optionId)) {
        updated.delete(optionId);
      } else {
        updated.add(optionId);
      }
      current.set(questionId, updated);
    } else {
      current.set(questionId, new Set([optionId]));
    }
    this.answers.set(current);
  }

  isSelected(questionId: number, optionId: number): boolean {
    return this.answers().get(questionId)?.has(optionId) ?? false;
  }

  goToQuestion(index: number) {
    const q = this.quiz();
    if (q && index >= 0 && index < q.questions.length) {
      this.currentIndex.set(index);
    }
  }

  nextQuestion() {
    this.goToQuestion(this.currentIndex() + 1);
  }

  prevQuestion() {
    this.goToQuestion(this.currentIndex() - 1);
  }

  submitQuiz() {
    const q = this.quiz();
    if (!q || this.submitting()) return;

    this.stopTimer();
    this.submitting.set(true);

    const timeTaken = Math.round((Date.now() - this.startTime) / 1000);
    const submission: SubmitQuizRequest = {
      playerName: this.playerName(),
      timeTakenSeconds: timeTaken,
      answers: q.questions.map((question) => ({
        questionId: question.id,
        selectedOptionIds: Array.from(this.answers().get(question.id) ?? []),
      })),
    };

    this.quizService.submitQuiz(q.id, submission).subscribe({
      next: (result) => {
        this.router.navigate(['/quizzes', q.id, 'result'], { state: { result } });
      },
      error: () => {
        this.submitting.set(false);
        alert('Failed to submit quiz. Please try again.');
      },
    });
  }
}
