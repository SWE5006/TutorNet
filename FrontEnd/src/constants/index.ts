import { Roles } from "../types/types";

export const STATUS_OPTIONS = [
  { value: "ACTIVE", label: "Active" },
  { value: "SUSPENDED", label: "Suspended" },
];
export const DATETIME_FORMAT = "DD/MM/YYYY HH:mm:ss";

export const HOME_MAPPING: Record<Roles, string> = {
  ADMIN: "/admindashboard",
  TUTOR: "/tutordashboard",
  STUDENT: "/dashboard",
};

export const TIME_FORMAT = "YYYY-MM-DDTHH:mm:ss";
