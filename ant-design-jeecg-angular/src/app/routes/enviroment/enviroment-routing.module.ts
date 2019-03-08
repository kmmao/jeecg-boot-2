import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EnviromentStandingBookInfoComponent } from './standing-book-info/standing-book-info.component';

const routes: Routes = [

  { path: 'standingBookInfo', component: EnviromentStandingBookInfoComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EnviromentRoutingModule { }
