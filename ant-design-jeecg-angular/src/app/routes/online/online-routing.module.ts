import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OnlineCgformComponent } from './cgform/cgform.component';
import { OnlineCgreportComponent } from './cgreport/cgreport.component';

const routes: Routes = [

  { path: 'cgform', component: OnlineCgformComponent },
  { path: 'cgreport', component: OnlineCgreportComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OnlineRoutingModule { }
