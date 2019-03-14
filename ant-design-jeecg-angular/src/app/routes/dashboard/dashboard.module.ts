import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardAnalysisComponent } from './analysis/analysis.component';
import { DashboardMonitorComponent } from './monitor/monitor.component';
import { DashboardWorkplaceComponent } from './workplace/workplace.component';

const COMPONENTS = [
  DashboardAnalysisComponent,
  DashboardMonitorComponent,
  DashboardWorkplaceComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    DashboardRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class DashboardModule { }
