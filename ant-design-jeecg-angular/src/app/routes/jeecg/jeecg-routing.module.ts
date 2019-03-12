import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { JeecgJeecgDemoListComponent } from './jeecg-demo-list/jeecg-demo-list.component';

const routes: Routes = [

  { path: 'jeecgDemoList', component: JeecgJeecgDemoListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class JeecgRoutingModule { }
