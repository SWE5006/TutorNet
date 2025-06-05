export interface Profile {
  fullName: unknown;
  bio: unknown;
  education: unknown;
  experience: unknown;
  userId: string;
  email: string;
  firstName: string;
  lastName: string;
  role: 'STUDENT' | 'TUTOR';
  subjects: Subject[];
  interests: string[];
  topics: string[];
  availability: TimeSlot[];
  priceRange?: PriceRange;  // For students
  hourlyRate?: number;      // For tutors
}

export interface Subject {
  id: string;
  name: string;
}

export interface TimeSlot {
  id: string;
  startTime: string;
  endTime: string;
  dayOfWeek: number;  // 0-6 for Sunday-Saturday
}

export interface PriceRange {
  min: number;
  max: number;
}

export interface ProfileUpdateRequest {
  subjects: string[];
  interests: string[];
  topics: string[];
  availability: TimeSlot[];
  priceRange?: PriceRange;
  hourlyRate?: number;
  experience:string;
  education:string;
  bio:string;
} 