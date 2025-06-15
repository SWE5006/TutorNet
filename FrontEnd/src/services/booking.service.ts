import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { commonHeader } from "../utils";
import { RootState } from "../state/store";

export const bookingReducerName = "bookingApi";

export const bookingApi = createApi({
  reducerPath: bookingReducerName,
  baseQuery: fetchBaseQuery({
    baseUrl: process.env.GATSBY_BACKEND_API_URL + "/api/booking",
    credentials: "include",
    prepareHeaders: commonHeader,
  }),
  tagTypes: ['Booking'],
  endpoints: (builder) => ({
    getBookingsByStudentId: builder.query({
      query: (studentId: string) => `?studentId=${studentId}`,
      providesTags: (result, error, studentId) => [{ type: 'Booking', id: studentId }],
    }),
  }),
});

export const { useGetBookingsByStudentIdQuery } = bookingApi;

export const createBooking = async (data: {
  studentId: string;
  slotId: string;
  subjectName: string;
  bookingStatus?: string;
}) => {
  const token = localStorage.getItem('token');
  const res = await fetch(`${process.env.GATSBY_BACKEND_API_URL}/api/booking`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {})
    },
    body: JSON.stringify(data)
  });
  if (!res.ok) throw new Error('Failed to create booking');
  return res.json();
};