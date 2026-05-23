import { Routes } from '@angular/router';
import { QuizList } from './components/quiz-list/quiz-list';
import { QuizDetail } from './components/quiz-detail/quiz-detail';
import { TakeQuiz } from './components/take-quiz/take-quiz';
import { QuizResultComponent } from './components/quiz-result/quiz-result';
import { CreateQuiz } from './components/create-quiz/create-quiz';

export const routes: Routes = [
  { path: '', redirectTo: '/quizzes', pathMatch: 'full' },
  { path: 'quizzes', component: QuizList },
  { path: 'quizzes/create', component: CreateQuiz },
  { path: 'quizzes/:id', component: QuizDetail },
  { path: 'quizzes/:id/take', component: TakeQuiz },
  { path: 'quizzes/:id/result', component: QuizResultComponent },
  { path: '**', redirectTo: '/quizzes' },
];
