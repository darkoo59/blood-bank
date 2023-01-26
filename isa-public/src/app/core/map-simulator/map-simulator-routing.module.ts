import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { MapSimulatorComponent } from "./map-simulator.component";

const routes: Routes = [
  {
    path: '', component: MapSimulatorComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MapSimulatorRoutingModule { }