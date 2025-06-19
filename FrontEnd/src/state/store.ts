import { configureStore, combineReducers } from "@reduxjs/toolkit";
import authReducer, { AUTH_SLICE_NAME } from "./auth/slice";
import { authApi } from "../services/auth.service";
import { userApi } from "../services/user.service";
import { profileApi } from "../services/profile.service";
import { bookingApi } from "../services/booking.service";

import {
  persistStore,
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from "redux-persist";
import createWebStorage from "redux-persist/lib/storage/createWebStorage";
import { tutorApi } from "../services/tutor.service";

const createNoopStorage = () => {
  return {
    getItem(_key: string) {
      return Promise.resolve(null);
    },
    setItem(_key: string, value: string) {
      return Promise.resolve(value);
    },
    removeItem(_key: string) {
      return Promise.resolve();
    },
  };
};

const storage =
  typeof window !== "undefined"
    ? createWebStorage("local")
    : createNoopStorage();

const persistConfig = {
  key: "auth",
  storage,
  whitelist: [AUTH_SLICE_NAME],
};

const rootReducer = combineReducers({
  [AUTH_SLICE_NAME]: authReducer,
  [authApi.reducerPath]: authApi.reducer,
  [userApi.reducerPath]: userApi.reducer,
  [profileApi.reducerPath]: profileApi.reducer,
  [tutorApi.reducerPath]: tutorApi.reducer,
  [bookingApi.reducerPath]: bookingApi.reducer,
});


const store = configureStore({
  reducer: persistReducer(persistConfig, rootReducer),
  middleware: (getDefaultMiddleware) => {
    return getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      },
    }).concat(authApi.middleware, userApi.middleware, profileApi.middleware,tutorApi.middleware, bookingApi.middleware);
  },
});

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
