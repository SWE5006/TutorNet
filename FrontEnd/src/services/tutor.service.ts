import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { commonHeader, fetchBaseQueryAuthMiddleware } from "../utils";
import { RootState } from "../state/store";

export const tutorReducerName = "tutorApi";

export const tutorApi = createApi({
  reducerPath: tutorReducerName,
  baseQuery: fetchBaseQuery({
    baseUrl: process.env.GATSBY_BACKEND_API_URL + "/api/tutors",
    credentials: "include",
    prepareHeaders: commonHeader,
  }),
  tagTypes: ['Tutor'],
  endpoints: (builder) => ({
    getTutors: builder.query<Tutor[], void>({
      query: () => `/all`,
      providesTags: (result) =>
        result
          ? [...result.map(({ id }) => ({ type: 'Tutor' as const, id })), { type: 'Tutor', id: 'LIST' }]
          : [{ type: 'Tutor', id: 'LIST' }],
    }),
    
    getTutorById: builder.query<Tutor, string>({
      query: (id) => `/${id}`,
      providesTags: (result, error, id) => [{ type: 'Tutor', id }],
    }),
  }),
});

// Define the Tutor interface
export interface Tutor {
  id: string;
  name: string;
  subject: string;
  description: string;
  location: string;
  rating: number;
}




export const { useGetTutorsQuery } = tutorApi;