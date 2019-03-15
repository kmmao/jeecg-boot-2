import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SysLinkComponent } from './link/link.component';

const routes: Routes = [
  { path: 'link/:url', component: SysLinkComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SysRoutingModule { }
