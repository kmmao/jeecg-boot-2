import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IsystemUserComponent } from './user/user.component';
import { IsystemRoleComponent } from './role/role.component';
import { IsystemDepartComponent } from './depart/depart.component';
import { IsystemPermissionComponent } from './permission/permission.component';
import { IsystemLogComponent } from './log/log.component';
import { IsystemDictComponent } from './dict/dict.component';
import { IsystemAnnountCementComponent } from './annount-cement/annount-cement.component';
import { IsystemQuartzJobListComponent } from './quartz-job-list/quartz-job-list.component';

const routes: Routes = [

  { path: 'user', component: IsystemUserComponent },
  { path: 'role', component: IsystemRoleComponent },
  { path: 'depart', component: IsystemDepartComponent },
  { path: 'permission', component: IsystemPermissionComponent },
  { path: 'log', component: IsystemLogComponent },
  { path: 'dict', component: IsystemDictComponent },
  { path: 'annountCement', component: IsystemAnnountCementComponent },
  { path: 'QuartzJobList', component: IsystemQuartzJobListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IsystemRoutingModule { }
