import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardAnalysisComponent } from './analysis/analysis.component';
import { DashboardMonitorComponent } from './monitor/monitor.component';
import { DashboardWorkplaceComponent } from './workplace/workplace.component';

const routes: Routes = [

  { path: 'analysis', component: DashboardAnalysisComponent },
  { path: 'monitor', component: DashboardMonitorComponent },
  { path: 'workplace', component: DashboardWorkplaceComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
