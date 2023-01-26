import { NgModule } from "@angular/core";
import { NgLetModule } from 'ng-let';
import { CommonModule } from '@angular/common';
import { MapSimulatorComponent } from "./map-simulator.component";
import { MapSimulatorService } from "./services/map-simulator.service";
import { MapSimulatorRoutingModule } from "./map-simulator-routing.module";
import { LeafletModule } from "@asymmetrik/ngx-leaflet";
import { MaterialModule } from "src/app/shared/material.module";
import { PageLoaderModule } from "src/app/shared/page-loader/page-loader.module";

@NgModule({
  providers: [MapSimulatorService],
  declarations: [MapSimulatorComponent],
  imports: [
    CommonModule,
    NgLetModule,
    MapSimulatorRoutingModule,
    LeafletModule,
    MaterialModule,
    PageLoaderModule
  ],
  exports: [MapSimulatorComponent]
})
export class MapSimulatorModule {}