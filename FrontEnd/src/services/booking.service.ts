import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { RootState } from "../state/store";
import { commonHeader } from "../utils";
import { BookingResponse, BookingRequest, Tutor, TutorTimeSlot } from "../types/types";



export const tutorReducerName = "bookingApi";

export const bookingApi = createApi({
  reducerPath: tutorReducerName,
  baseQuery: fetchBaseQuery({
    baseUrl: process.env.GATSBY_BACKEND_API_URL + "/api/bookings",
    credentials: "include",
    prepareHeaders: commonHeader,
  }),
  tagTypes: ['Booking'],
  endpoints: (builder) => ({
    getBookingByEmail: builder.query<BookingResponse[], string>({
      query: (email) => ({
        url: `/booking`,
        method: 'POST',
        body:email
      }),
      providesTags: ['Booking']
    }),
    getBookingByTutorId: builder.query<BookingResponse[], string>({
      query: (tutorid) => ({
        url: `/tutor/${tutorid}`,
        method: 'GET'
      }),
      providesTags: ['Booking']
    }),
      addBooking: builder.mutation<void, BookingRequest>({
          query: (payload) => ({
            url: "/create",
            method: "POST",
            body: payload,
          }),
          invalidatesTags: ["Booking"],
        }),
    
  }),
  
  
});

export const { useGetBookingByEmailQuery,useAddBookingMutation,useGetBookingByTutorIdQuery } = bookingApi;
