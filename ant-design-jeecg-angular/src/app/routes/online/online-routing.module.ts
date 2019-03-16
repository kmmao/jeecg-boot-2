import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OnlineCgformComponent } from './cgform/cgform.component';

const routes: Routes = [

  { path: 'cgform', component: OnlineCgformComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OnlineRoutingModule { }
