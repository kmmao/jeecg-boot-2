import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { OutdoorRoutingModule } from './outdoor-routing.module';
import { OutdoorReportComponent } from './report/report.component';

const COMPONENTS = [
  OutdoorReportComponent
  ];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    OutdoorRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class OutdoorModule { }
