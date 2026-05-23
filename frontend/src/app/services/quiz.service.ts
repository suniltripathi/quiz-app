import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  QuizSummary,
  QuizDetail,
  CreateQuizRequest,
  SubmitQuizRequest,
  QuizResult,
} from '../models/quiz.model';

@Injectable({
  providedIn: 'root',
})
export class QuizService {
  private apiUrl = 'http://localhost:8080/api/quizzes';

  constructor(private http: HttpClient) {}

  getQuizzes(): Observable<QuizSummary[]> {
    return this.http.get<QuizSummary[]>(this.apiUrl);
  }

  getQuiz(id: number): Observable<QuizDetail> {
    return this.http.get<QuizDetail>(`${this.apiUrl}/${id}`);
  }

  getQuizWithAnswers(id: number): Observable<QuizDetail> {
    return this.http.get<QuizDetail>(`${this.apiUrl}/${id}/admin`);
  }

  createQuiz(quiz: CreateQuizRequest): Observable<QuizDetail> {
    return this.http.post<QuizDetail>(this.apiUrl, quiz);
  }

  deleteQuiz(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  submitQuiz(quizId: number, submission: SubmitQuizRequest): Observable<QuizResult> {
    return this.http.post<QuizResult>(`${this.apiUrl}/${quizId}/submit`, submission);
  }
}
