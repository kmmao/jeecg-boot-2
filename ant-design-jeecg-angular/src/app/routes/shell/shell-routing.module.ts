import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShellXtermComponent } from './xterm/xterm.component';

const routes: Routes = [

  { path: 'xterm', component: ShellXtermComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShellRoutingModule { }
