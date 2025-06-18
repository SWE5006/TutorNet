import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { RootState } from "../state/store";
import { commonHeader } from "../utils";
import { TutorTimeSlot } from "../types/types";

export const tutorReducerName = "tutorApi";

// Define Tutor interface if not already defined
export interface Tutor {
  id: string; // UUID from backend
  username: string; // Changed from name
  bio: string;
  education: string; // Added new field
  experience: string; // Added new field
  hourlyRate: number; // Added new field as number type for Double
  subjects: string; // Changed from subject
}

export const tutorApi = createApi({
  reducerPath: tutorReducerName,
  baseQuery: fetchBaseQuery({
    baseUrl: process.env.GATSBY_BACKEND_API_URL + "/api/tutors",
    credentials: "include",
    prepareHeaders: commonHeader,
  }),
  tagTypes: ["TimeSlots"],
  endpoints: (builder) => ({
    getTutors: builder.query<Tutor[], void>({
      query: () => `/all`,
      providesTags: (result) =>
        result
          ? [
              ...result.map(({ id }) => ({ type: "Tutor" as const, id })),
              { type: "Tutor", id: "LIST" },
            ]
          : [{ type: "Tutor", id: "LIST" }],
    }),
    getTutorTimeSlots: builder.query<TutorTimeSlot[], string>({
      query: () => ({
        url: `/timeslots/by-id`,
        method: "GET",
      }),
      providesTags: ["TimeSlots"],
    }),
    addTimeSlot: builder.mutation<any, TutorTimeSlot>({
      query: (payload) => ({
        url: `/timeslots`,
        method: "POST",
        body: payload,
      }),
      invalidatesTags: ["TimeSlots"],
    }),
    deleteTimeSlot: builder.mutation<any, string>({
      query: (timeSlotId) => ({
        url: `/timeslots/${timeSlotId}/delete`,
        method: "DELETE",
      }),
      invalidatesTags: ["TimeSlots"],
    }),
  }),
});

export const {
  useGetTutorsQuery,
  useGetTutorTimeSlotsQuery,
  useAddTimeSlotMutation,
  useDeleteTimeSlotMutation,
} = tutorApi;
