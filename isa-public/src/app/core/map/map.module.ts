import { NgModule } from "@angular/core";
import { LeafletModule } from "@asymmetrik/ngx-leaflet";
import { MapComponent } from "./map.component";

@NgModule({
  declarations: [
    MapComponent
  ],
  imports: [
    LeafletModule
  ],
  exports: [
    MapComponent
  ]
})
export class MapModule { }