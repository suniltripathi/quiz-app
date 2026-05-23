export interface QuizSummary {
  id: number;
  title: string;
  description: string;
  timeLimitMinutes: number | null;
  active: boolean;
  questionCount: number;
}

export interface QuizDetail {
  id: number;
  title: string;
  description: string;
  timeLimitMinutes: number | null;
  active: boolean;
  questions: Question[];
}

export interface Question {
  id: number;
  text: string;
  questionType: 'SINGLE_CHOICE' | 'MULTIPLE_CHOICE' | 'TRUE_FALSE';
  orderIndex: number;
  points: number;
  options: Option[];
}

export interface Option {
  id: number;
  text: string;
  correct: boolean | null;
}

export interface CreateQuizRequest {
  title: string;
  description: string;
  timeLimitMinutes: number | null;
  questions: CreateQuestionRequest[];
}

export interface CreateQuestionRequest {
  text: string;
  questionType: string;
  points: number;
  options: CreateOptionRequest[];
}

export interface CreateOptionRequest {
  text: string;
  correct: boolean;
}

export interface SubmitQuizRequest {
  playerName: string;
  timeTakenSeconds: number | null;
  answers: AnswerRequest[];
}

export interface AnswerRequest {
  questionId: number;
  selectedOptionIds: number[];
}

export interface QuizResult {
  attemptId: number;
  playerName: string;
  quizTitle: string;
  totalQuestions: number;
  correctAnswers: number;
  totalPoints: number;
  earnedPoints: number;
  percentage: number;
  timeTakenSeconds: number | null;
  questionResults: QuestionResult[];
}

export interface QuestionResult {
  questionId: number;
  questionText: string;
  correct: boolean;
  selectedAnswers: string[];
  correctAnswers: string[];
  pointsEarned: number;
}
