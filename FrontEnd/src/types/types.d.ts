type Roles = "TUTOR" | "STUDENT" | "ADMIN";

interface IUser {
  userId?: string;
  userName: string;
  mobileNumber: string;
  emailAddress: string;
  activeStatus: string;
  roles: string;
  createDt?: string;
}

interface IUserDetail extends IUser {
  roleName: string;
  permission: string;
}

interface LoginRequest {
  emailAddress: string;
  password: string;
}

interface LoginResponse {
  access_token: string;
  access_token_expiry: number;
  token_type: string;
  user_name: string;
  email_address: string;
  user_role: SgehRoles;
}

interface SignupRequest {
  userName: string;
  userEmail: string;
  userMobileNo: string;
  userPassword: string;
  userRole: SgehRoles;
}

interface SignupRequestStudent {
  username: string;
  password: string;
  emailAddress: string;
  mobileNumber: string;
  age: string;
  classLevel: string;
  userRole: string;
}

interface SignupRequestTutor {
  username: string;
  password: string;
  emailAddress: string;
  mobileNumber: string;
  hourlyRate: string;
  education: string;
  experience: string;
  location: string;
  userRole: string;
  bio: string;
  teachingSubjects?: string[];
}

interface Tutor {
  id: string;           // UUID from backend
  username: string;     // Changed from name
  bio: string;         
  education: string;    // Added new field
  experience: string;   // Added new field
  hourlyRate: number;   // Added new field as number type for Double
  subjects: string;     // Changed from subject array to string
}