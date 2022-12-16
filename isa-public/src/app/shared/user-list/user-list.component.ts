import { Component, Input } from "@angular/core";
import { User } from "src/app/model/user.model";

@Component({
  selector: 'user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent { 
  @Input() i_Users: User[] = [];
  m_Columns: string[] = ['id', 'firstname', 'lastname', 'email'];
}