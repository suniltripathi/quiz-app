import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { QuizService } from '../../services/quiz.service';
import { QuizSummary } from '../../models/quiz.model';

@Component({
  selector: 'app-quiz-list',
  imports: [RouterLink],
  templateUrl: './quiz-list.html',
  styleUrl: './quiz-list.scss',
})
export class QuizList implements OnInit {
  quizzes = signal<QuizSummary[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  constructor(private quizService: QuizService) {}

  ngOnInit() {
    this.loadQuizzes();
  }

  loadQuizzes() {
    this.loading.set(true);
    this.error.set(null);
    this.quizService.getQuizzes().subscribe({
      next: (quizzes) => {
        this.quizzes.set(quizzes);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set('Failed to load quizzes. Please make sure the backend server is running.');
        this.loading.set(false);
        console.error('Error loading quizzes:', err);
      },
    });
  }
}
