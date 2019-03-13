import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DemoDemoListComponent } from './demo-list/demo-list.component';
import { DemoFlowTestComponent } from './flow-test/flow-test.component';

const routes: Routes = [

  { path: 'jeecgDemoList', component: DemoDemoListComponent },
  { path: 'FlowTest', component: DemoFlowTestComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DemoRoutingModule { }
