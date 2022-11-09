import { Address } from "./address.model";

export interface BranchCenter {
  id: number;
  name: string;
  description: string;
  address: Address;
}