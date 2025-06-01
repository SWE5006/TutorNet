import { createApi } from "@reduxjs/toolkit/query/react";
import { commonHeader, fetchBaseQueryAuthMiddleware } from "../utils";
import { RootState } from "../state/store";

export const userReducerName = "tutorApi";

