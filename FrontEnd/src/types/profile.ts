export interface TeachingAvailability {
  dayOfWeek: string;
  startTime: string;
  endTime: string;
  status: string;
}

export interface Profile {
  userId: string;
  email: string;
  fullName: string;
  role: string;
  bio: string;
  education: string;
  experience: string;
  interestedSubjects: string[];
  topics: string[];
  minBudget: number;
  maxBudget: number;
  teachingSubjects: string;
  hourlyRate: number;
  teachingAvailability: TeachingAvailability[];
  createdAt: string;
  lastUpdated: string;
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
  userId: string;
  subjects: string[];
  interests: string[];
  topics: string[];
  availability: TimeSlot[];
  priceRange?: PriceRange;
  hourlyRate?: number;
  experience: string;
  education: string;
  bio: string;
  teachingAvailability?: TimeSlot[];
  teachingSubjects?: string[];
}