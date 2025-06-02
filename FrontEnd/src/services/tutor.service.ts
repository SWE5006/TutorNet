import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { commonHeader, fetchBaseQueryAuthMiddleware } from "../utils";
import { RootState } from "../state/store";

export const tutorReducerName = "tutorApi";

export const tutorApi = createApi({
  reducerPath: tutorReducerName,
  baseQuery: fetchBaseQuery({
    baseUrl: process.env.GATSBY_BACKEND_API_URL + "/api/tutor",
    credentials: "include",
    prepareHeaders: commonHeader,
  }),
  tagTypes: ['Tutor'],
  endpoints: (builder) => ({
    getTutorById: builder.query({
      query: (tutorId: string) => `/${tutorId}`,
      providesTags: (result, error, tutorId) => [{ type: 'Tutor', id: tutorId }],
    }),
  }),
});

export const { useGetTutorByIdQuery } = tutorApi;