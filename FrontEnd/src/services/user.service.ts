import { createApi } from "@reduxjs/toolkit/query/react";
import { commonHeader, fetchBaseQueryAuthMiddleware } from "../utils";
import { RootState } from "../state/store";
import { IUser, IUserDetail } from "../types/types";

export const userReducerName = "userApi";

export const userApi = createApi({
  reducerPath: userReducerName,
  baseQuery: fetchBaseQueryAuthMiddleware({
    baseUrl: process.env.GATSBY_BACKEND_API_URL + "/api/user",
    prepareHeaders: commonHeader,
    credentials: "include",
  }),
  tagTypes: ["UserList", "UserDetails"],
  refetchOnMountOrArgChange: true,
  endpoints: (builder) => ({
    getUserList: builder.query<IUser[], void>({
      query: () => ({
        url: "/all",
        method: "GET",
      }),
      providesTags: ["UserList"],
    }),
    getUserDetails: builder.query<IUserDetail, string>({
      query: (userId) => ({
        url: `/${userId}/details`,
        method: "GET",
      }),
      providesTags: ["UserDetails"],
    }),
    updateUser: builder.mutation<void, IUser>({
      query: (payload) => ({
        url: "/update",
        method: "POST",
        body: payload,
      }),
      invalidatesTags: ["UserList", "UserDetails"],
    }),
    addUser: builder.mutation<void, IUser>({
      query: (payload) => ({
        url: "/create",
        method: "POST",
        body: payload,
      }),
      invalidatesTags: ["UserList"],
    }),
    deleteUser: builder.mutation<void, string>({
      query: (userId) => ({
        url: `/${userId}/delete`,
        method: "DELETE",
      }),
      invalidatesTags: ["UserList"],
    }),
  }),
});

export const userReducerSelector = (state: RootState) => state[userReducerName];

export const {
  useGetUserListQuery,
  useGetUserDetailsQuery,
  useUpdateUserMutation,
  useAddUserMutation,
  useDeleteUserMutation,
} = userApi;
