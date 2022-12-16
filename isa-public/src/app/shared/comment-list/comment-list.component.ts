import { Component, Input } from "@angular/core";
import { Comment } from "src/app/model/comment.model";

@Component({
  selector: 'comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.scss'],
})
export class CommentListComponent {
  @Input() i_Comments: Comment[] = [];
}