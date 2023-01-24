import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { LeafletModule } from "@asymmetrik/ngx-leaflet";
import { NgLetModule } from "ng-let";
import { MapComponent } from "./map.component";
import { MapService } from "./map.service";

@NgModule({
  providers: [MapService],
  declarations: [
    MapComponent
  ],
  imports: [
    CommonModule,
    LeafletModule,
    NgLetModule
  ],
  exports: [
    MapComponent
  ]
})
export class MapModule { }