import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { RootState } from "../state/store";
import { commonHeader } from "../utils";
import { BookingResponse, Tutor, TutorTimeSlot } from "../types/types";


export const tutorReducerName = "bookingApi";

export const tutorApi = createApi({
  reducerPath: tutorReducerName,
  baseQuery: fetchBaseQuery({
    baseUrl: process.env.GATSBY_BACKEND_API_URL + "/api/bookings",
    credentials: "include",
    prepareHeaders: commonHeader,
  }),
  tagTypes: ['Booking'],
  endpoints: (builder) => ({
    getBookingByStudentId: builder.query<BookingResponse[], string>({
      query: (studentid) => ({
        url: `/${studentid}`,
        method: 'GET'
      }),
      providesTags: ['Booking']
    }),
  }),
});

export const { useGetBookingByStudentIdQuery } = tutorApi;