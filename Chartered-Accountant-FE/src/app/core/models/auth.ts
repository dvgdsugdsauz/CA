import { IsoDateTime } from './common';

export type AdminRole = 'ADMIN' | 'STAFF';

/** `AdminUserResponse.java`, also returned as the signed-in user's profile. */
export interface AdminUser {
  userId: number;
  username: string;
  fullName: string;
  email: string;
  role: AdminRole;
  enabled: boolean;
  createdAt: IsoDateTime;
  lastLoginAt: IsoDateTime | null;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  tokenType: string;
  /** Token lifetime in seconds. */
  expiresIn: number;
  user: AdminUser;
}

/** What the browser keeps for a signed-in session. */
export interface AuthSession {
  token: string;
  /** Epoch milliseconds. */
  expiresAt: number;
  user: AdminUser;
}

export interface ChangePasswordRequest {
  currentPassword: string;
  /** 8 to 64 characters, with at least one letter and one number. */
  newPassword: string;
}

/** `AdminUserRequest.java`. `password` is required on create; leave empty on update to keep it. */
export interface AdminUserRequest {
  userId: number | null;
  username: string;
  fullName: string;
  email: string;
  role: AdminRole;
  enabled?: boolean;
  password?: string | null;
}
