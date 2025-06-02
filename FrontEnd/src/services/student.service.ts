import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { commonHeader, fetchBaseQueryAuthMiddleware } from "../utils";
import { RootState } from "../state/store";
export const studentReducerName = "studentApi";

export const studentApi = createApi({
  reducerPath: studentReducerName,
  baseQuery: fetchBaseQuery({
    baseUrl: process.env.GATSBY_BACKEND_API_URL + "/api/student",
    credentials: "include",
    prepareHeaders: commonHeader,
  }),
  tagTypes: ['Student'],
  endpoints: (builder) => ({
    getStudentById: builder.query({
      query: (studentId: string) => `/${studentId}`,
      providesTags: (result, error, studentId) => [{ type: 'Student', id: studentId }],
    }),
  }),
});

export const { useGetStudentByIdQuery } = studentApi;