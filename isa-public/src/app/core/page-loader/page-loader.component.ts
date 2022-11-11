import { Component, Input } from "@angular/core";

@Component({
  selector: 'page-loader',
  templateUrl: './page-loader.component.html',
  styleUrls: ['./page-loader.component.scss']
})
export class PageLoaderComponent {
  @Input() m_ItemNumber: number = 8;
  m_Numbers = Array(this.m_ItemNumber).map((x,i)=>i);
  
}