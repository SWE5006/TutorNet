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