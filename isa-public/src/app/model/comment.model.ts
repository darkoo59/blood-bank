import { User } from "./user.model";

export interface Comment {
  id: number;
  content: string;
  grade: number;
  postedOn: Date;
  user: User;
}