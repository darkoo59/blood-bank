import { Role } from "./role.model";

import { Address } from "./address.model";

export interface User {
  id: number;
  firstname: string;
  lastname: string;
  email: string;
  roles?: Role[];
  address: Address | null;
  phone: string;
  nationalId: any;
  sex: string;
  occupation: string;
  information: string;
  penalties?: number;
  passwordChange?: boolean;
}
