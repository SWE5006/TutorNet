import { RootState } from '../state/store';
import { selectAuthSlice } from '../state/auth/slice';
import store from '../state/store';

export interface TimeSlotDto {
  startTime: string;
  endTime: string;
  dayOfWeek: number;
}

export interface InterestRequestDto {
  userId: string;
  tutorId: string;
  availableTimeSlots: TimeSlotDto[];
}

export const submitInterest = async (data: InterestRequestDto) => {
  const state: RootState = store.getState();
  const token = state.auth.userInfo?.access_token;
  const response = await fetch(`${process.env.GATSBY_BACKEND_API_URL}/api/interest`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
    },
    credentials: 'include',
    body: JSON.stringify(data),
  });
  if (!response.ok) throw new Error('Failed to submit interest');
  return response.json();
}; 