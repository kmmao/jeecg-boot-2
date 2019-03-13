import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { DemoRoutingModule } from './demo-routing.module';
import { DemoDemoListComponent } from './demo-list/demo-list.component';
import { DemoDemoListAddComponent } from './demo-list/demo-list-add/demo-list-add.component';
import { DemoDemoListEditComponent } from './demo-list/demo-list-edit/demo-list-edit.component';
import { DemoFlowTestComponent } from './flow-test/flow-test.component';

const COMPONENTS = [
  DemoDemoListComponent,
  DemoFlowTestComponent];
const COMPONENTS_NOROUNT = [
  DemoDemoListAddComponent,
  DemoDemoListEditComponent];

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
