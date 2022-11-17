import { Address } from "./address.model";
import { User } from "./user.model";

export interface BranchCenter {
  id: number;
  name: string;
  description: string;
  address: Address;
  admins: User[];
}