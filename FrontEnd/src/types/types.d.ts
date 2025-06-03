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
  userName: string;
  userEmail: string;
  userMobileNo: string;
  userPassword: string;
  userAge: string;
  userClassLevel: string;
  userRole: string;
}

interface SignupRequestTutor {
      userName:string;
      userEmail:string;
      userPassword:string;
      userMobileNo:string;
      hourlyRate:string;
      qualification:string;
      experienceYears:string;
      location:string;
      userRole: string;
}