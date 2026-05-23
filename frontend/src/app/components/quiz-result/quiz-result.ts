import { Component, OnInit, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { DecimalPipe } from '@angular/common';
import { QuizResult } from '../../models/quiz.model';

@Component({
  selector: 'app-quiz-result',
  imports: [RouterLink, DecimalPipe],
  templateUrl: './quiz-result.html',
  styleUrl: './quiz-result.scss',
})
export class QuizResultComponent implements OnInit {
  result = signal<QuizResult | null>(null);

  constructor(private router: Router) {}

  ngOnInit() {
    const state = this.router.getCurrentNavigation()?.extras.state ?? history.state;
    if (state?.['result']) {
      this.result.set(state['result'] as QuizResult);
    }
  }

  getGrade(): string {
    const r = this.result();
    if (!r) return '';
    const pct = r.percentage;
    if (pct >= 90) return 'A+';
    if (pct >= 80) return 'A';
    if (pct >= 70) return 'B';
    if (pct >= 60) return 'C';
    if (pct >= 50) return 'D';
    return 'F';
  }

  getGradeClass(): string {
    const grade = this.getGrade();
    if (grade.startsWith('A')) return 'grade-excellent';
    if (grade === 'B') return 'grade-good';
    if (grade === 'C') return 'grade-average';
    return 'grade-poor';
  }

  formatTime(seconds: number | null): string {
    if (!seconds) return '--';
    const m = Math.floor(seconds / 60);
    const s = seconds % 60;
    return `${m}m ${s}s`;
  }
}
