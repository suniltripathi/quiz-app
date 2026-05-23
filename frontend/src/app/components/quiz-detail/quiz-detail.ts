import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { QuizService } from '../../services/quiz.service';
import { QuizDetail as QuizDetailModel } from '../../models/quiz.model';

@Component({
  selector: 'app-quiz-detail',
  imports: [RouterLink],
  templateUrl: './quiz-detail.html',
  styleUrl: './quiz-detail.scss',
})
export class QuizDetail implements OnInit {
  quiz = signal<QuizDetailModel | null>(null);
  loading = signal(true);
  quizId = 0;

  constructor(
    private route: ActivatedRoute,
    private quizService: QuizService,
  ) {}

  ngOnInit() {
    this.quizId = Number(this.route.snapshot.paramMap.get('id'));
    this.quizService.getQuiz(this.quizId).subscribe({
      next: (quiz) => {
        this.quiz.set(quiz);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  getTotalPoints(): number {
    const q = this.quiz();
    if (!q) return 0;
    return q.questions.reduce((sum, question) => sum + question.points, 0);
  }

  getQuestionTypes(): { name: string; count: number }[] {
    const q = this.quiz();
    if (!q) return [];
    const types: Record<string, number> = {};
    for (const question of q.questions) {
      const label = question.questionType.replace(/_/g, ' ').toLowerCase();
      const formatted = label.charAt(0).toUpperCase() + label.slice(1);
      types[formatted] = (types[formatted] || 0) + 1;
    }
    return Object.entries(types).map(([name, count]) => ({ name, count }));
  }
}
