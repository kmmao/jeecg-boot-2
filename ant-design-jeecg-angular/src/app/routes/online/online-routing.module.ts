import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OnlineCgformComponent } from './cgform/cgform.component';
import { OnlineCgreportComponent } from './cgreport/cgreport.component';
import { OnlineCgformListComponent } from './cgform-list/cgform-list.component';

const routes: Routes = [

  { path: 'cgform', component: OnlineCgformComponent },
  { path: 'cgreport', component: OnlineCgreportComponent },
  { path: 'cgformList/:code', component: OnlineCgformListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OnlineRoutingModule { }
