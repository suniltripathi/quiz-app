import { Component, signal } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { QuizService } from '../../services/quiz.service';
import { CreateQuizRequest, CreateQuestionRequest, CreateOptionRequest } from '../../models/quiz.model';

@Component({
  selector: 'app-create-quiz',
  imports: [FormsModule],
  templateUrl: './create-quiz.html',
  styleUrl: './create-quiz.scss',
})
export class CreateQuiz {
  title = signal('');
  description = signal('');
  timeLimitMinutes = signal<number | null>(null);
  questions = signal<QuestionForm[]>([createEmptyQuestion()]);
  submitting = signal(false);
  error = signal<string | null>(null);

  constructor(
    private quizService: QuizService,
    private router: Router,
  ) {}

  addQuestion() {
    this.questions.update((qs) => [...qs, createEmptyQuestion()]);
  }

  removeQuestion(index: number) {
    if (this.questions().length <= 1) return;
    this.questions.update((qs) => qs.filter((_, i) => i !== index));
  }

  addOption(questionIndex: number) {
    this.questions.update((qs) => {
      const updated = [...qs];
      updated[questionIndex] = {
        ...updated[questionIndex],
        options: [...updated[questionIndex].options, { text: '', correct: false }],
      };
      return updated;
    });
  }

  removeOption(questionIndex: number, optionIndex: number) {
    const q = this.questions()[questionIndex];
    if (q.options.length <= 2) return;
    this.questions.update((qs) => {
      const updated = [...qs];
      updated[questionIndex] = {
        ...updated[questionIndex],
        options: updated[questionIndex].options.filter((_, i) => i !== optionIndex),
      };
      return updated;
    });
  }

  updateQuestionText(qIndex: number, text: string) {
    this.questions.update((qs) => {
      const updated = [...qs];
      updated[qIndex] = { ...updated[qIndex], text };
      return updated;
    });
  }

  updateQuestionType(qIndex: number, type: string) {
    this.questions.update((qs) => {
      const updated = [...qs];
      let options = updated[qIndex].options;
      if (type === 'TRUE_FALSE') {
        options = [
          { text: 'True', correct: false },
          { text: 'False', correct: false },
        ];
      }
      updated[qIndex] = { ...updated[qIndex], questionType: type, options };
      return updated;
    });
  }

  updateQuestionPoints(qIndex: number, points: number) {
    this.questions.update((qs) => {
      const updated = [...qs];
      updated[qIndex] = { ...updated[qIndex], points };
      return updated;
    });
  }

  updateOptionText(qIndex: number, oIndex: number, text: string) {
    this.questions.update((qs) => {
      const updated = [...qs];
      const options = [...updated[qIndex].options];
      options[oIndex] = { ...options[oIndex], text };
      updated[qIndex] = { ...updated[qIndex], options };
      return updated;
    });
  }

  toggleCorrect(qIndex: number, oIndex: number) {
    this.questions.update((qs) => {
      const updated = [...qs];
      const q = updated[qIndex];
      const options = [...q.options];

      if (q.questionType === 'MULTIPLE_CHOICE') {
        options[oIndex] = { ...options[oIndex], correct: !options[oIndex].correct };
      } else {
        for (let i = 0; i < options.length; i++) {
          options[i] = { ...options[i], correct: i === oIndex };
        }
      }

      updated[qIndex] = { ...updated[qIndex], options };
      return updated;
    });
  }

  isValid(): boolean {
    if (!this.title().trim()) return false;
    for (const q of this.questions()) {
      if (!q.text.trim()) return false;
      if (q.options.some((o) => !o.text.trim())) return false;
      if (!q.options.some((o) => o.correct)) return false;
    }
    return true;
  }

  submitQuiz() {
    if (!this.isValid() || this.submitting()) return;

    this.submitting.set(true);
    this.error.set(null);

    const request: CreateQuizRequest = {
      title: this.title(),
      description: this.description(),
      timeLimitMinutes: this.timeLimitMinutes(),
      questions: this.questions().map((q): CreateQuestionRequest => ({
        text: q.text,
        questionType: q.questionType,
        points: q.points,
        options: q.options.map((o): CreateOptionRequest => ({
          text: o.text,
          correct: o.correct,
        })),
      })),
    };

    this.quizService.createQuiz(request).subscribe({
      next: (created) => {
        this.router.navigate(['/quizzes', created.id]);
      },
      error: (err) => {
        this.submitting.set(false);
        this.error.set('Failed to create quiz. Please try again.');
        console.error('Create quiz error:', err);
      },
    });
  }
}

interface QuestionForm {
  text: string;
  questionType: string;
  points: number;
  options: { text: string; correct: boolean }[];
}

function createEmptyQuestion(): QuestionForm {
  return {
    text: '',
    questionType: 'SINGLE_CHOICE',
    points: 1,
    options: [
      { text: '', correct: false },
      { text: '', correct: false },
      { text: '', correct: false },
      { text: '', correct: false },
    ],
  };
}
