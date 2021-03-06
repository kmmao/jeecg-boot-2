import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { DemoRoutingModule } from './demo-routing.module';
import { DemoDemoListComponent } from './demo-list/demo-list.component';
import { DemoDemoListAddComponent } from './demo-list/demo-list-add/demo-list-add.component';
import { DemoDemoListEditComponent } from './demo-list/demo-list-edit/demo-list-edit.component';
import { DemoFlowTestComponent } from './flow-test/flow-test.component';
import { DemoOneToManyTabComponent } from './one-to-many-tab/one-to-many-tab.component';
import { DemoOneToManyComponent } from './one-to-many/one-to-many.component';
import { DemoPrintDemoListComponent } from './print-demo-list/print-demo-list.component';
import { DemoHelloworldComponent } from './helloworld/helloworld.component';
import { DemoOnlineComponent } from './online/online.component';
import { DemoOnlineEditComponent } from './online/online-edit/online-edit.component';
import { DemoOnlineAddComponent } from './online/online-add/online-add.component';

const COMPONENTS = [
  DemoDemoListComponent,
  DemoFlowTestComponent,
  DemoOneToManyTabComponent,
  DemoOneToManyComponent,
  DemoPrintDemoListComponent,
  DemoHelloworldComponent,
  DemoOnlineComponent];
const COMPONENTS_NOROUNT = [
  DemoDemoListAddComponent,
  DemoDemoListEditComponent,
  DemoOnlineEditComponent,
  DemoOnlineAddComponent];

@NgModule({
  imports: [
    SharedModule,
    DemoRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class DemoModule { }
