import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OutdoorReportComponent } from './report/report.component';

const routes: Routes = [
  { path: 'report', component: OutdoorReportComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OutdoorRoutingModule { }
