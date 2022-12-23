import { BranchCenter } from "./branch-center.model";
import { User } from "./user.model";

export interface Complaint {
    id: number;
    text: string;
    user: User;
    branchCenter: BranchCenter;
}
