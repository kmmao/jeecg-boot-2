import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DemoDemoListComponent } from './demo-list/demo-list.component';
import { DemoFlowTestComponent } from './flow-test/flow-test.component';
import { DemoOneToManyTabComponent } from './one-to-many-tab/one-to-many-tab.component';
import { DemoOneToManyComponent } from './one-to-many/one-to-many.component';
import { DemoPrintDemoListComponent } from './print-demo-list/print-demo-list.component';
import { DemoHelloworldComponent } from './helloworld/helloworld.component';

const routes: Routes = [

  { path: 'jeecgDemoList', component: DemoDemoListComponent },
  { path: 'FlowTest', component: DemoFlowTestComponent },
  { path: 'JeecgOrderDMainList', component: DemoOneToManyTabComponent },
  { path: 'JeecgOrderMainList', component: DemoOneToManyComponent },
  { path: 'PrintDemoList', component: DemoPrintDemoListComponent },
  { path: 'helloworld', component: DemoHelloworldComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DemoRoutingModule { }
