import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { RootState } from "../state/store";
import { commonHeader } from "../utils";
import { Profile, ProfileUpdateRequest } from "../types/profile";

export const profileReducerName = "profileApi";

export const profileApi = createApi({
  reducerPath: profileReducerName,
  baseQuery: fetchBaseQuery({
    baseUrl: process.env.GATSBY_BACKEND_API_URL + "/api/profile",
    credentials: "include",
    prepareHeaders: commonHeader,
  }),
  endpoints: (builder) => ({
    getCurrentProfile: builder.query<Profile, String>({
      query: (email) => `/current/${email}`,
    }),
    getProfile: builder.query<Profile, string>({
      query: (userId) => `/${userId}`,
    }),
    getStudentProfile: builder.query<Profile, string>({
      query: (studentId) => `/student/${studentId}`,
    }),
    getTutorProfile: builder.query<Profile, string>({
      query: (tutorId) => `/tutor/${tutorId}`,
    }),
    updateProfile: builder.mutation<Profile, { userId: string; data: ProfileUpdateRequest }>({
      query: ({ userId, data }) => ({
        url: `/${userId}`,
        method: "PUT",
        body: data,
      }),
    }),
  }),
});

export const {
  useGetCurrentProfileQuery,
  useGetProfileQuery,
  useGetStudentProfileQuery,
  useGetTutorProfileQuery,
  useUpdateProfileMutation,
} = profileApi; 