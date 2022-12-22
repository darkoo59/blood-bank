import { Donation } from "./donation.model";
import { User } from "./user.model";

export interface Appointment {
  id: number;
  title: string;
  begin: Date | null;
  end: Date | null;
  started: boolean;
  user: User;
  donation?: Donation;
}