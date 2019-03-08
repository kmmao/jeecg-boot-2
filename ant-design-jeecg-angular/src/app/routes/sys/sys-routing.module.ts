import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SysRoleComponent } from './role/role.component';
import { SysLinkComponent } from './link/link.component';

const routes: Routes = [

  { path: 'role', component: SysRoleComponent },
  { path: 'link/:url', component: SysLinkComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SysRoutingModule { }
