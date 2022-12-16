import { User } from "./user.model";

export interface Appointment {
  id: number;
  begin: Date | null;
  end: Date | null;
  started: boolean;
  user: User;
}