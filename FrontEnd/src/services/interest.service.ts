import { RootState } from '../state/store';
import { selectAuthSlice } from '../state/auth/slice';
import store from '../state/store';



export interface InterestRequestDto {
  studentEmail:string;
  subjectName: string;
  slotId: string[];
  numberOfBooking: number;
  tutorId: string;
  bookingDate: String;
}


export const submitInterest = async (data: InterestRequestDto) => {
  const state: RootState = store.getState();
  const token = state.auth.userInfo?.access_token;
  const response = await fetch(`${process.env.GATSBY_BACKEND_API_URL}/api/booking/create`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
    },
    credentials: 'include',
    body: JSON.stringify(data),
  });
  if (!response.ok) {
    console.error('Failed to submit interest:', response.statusText);
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  return response.json();
}; 