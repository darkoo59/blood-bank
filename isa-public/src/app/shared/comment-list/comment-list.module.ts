import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "../material.module";
import { CommentListComponent } from "./comment-list.component";
import { GradeComponent } from "./grade.component";
import { StarComponent } from "./star.component";

@NgModule({
  declarations: [CommentListComponent, GradeComponent, StarComponent],
  imports: [
    CommonModule,
    NgLetModule,
    MaterialModule
  ],
  exports: [CommentListComponent, GradeComponent]
})
export class CommentListModule { }