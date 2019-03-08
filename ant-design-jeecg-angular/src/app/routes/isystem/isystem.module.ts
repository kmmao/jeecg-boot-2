import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { IsystemRoutingModule } from './isystem-routing.module';
import { IsystemUserComponent } from './user/user.component';
import { IsystemRoleComponent } from './role/role.component';
import { IsystemDepartComponent } from './depart/depart.component';
import { IsystemPermissionComponent } from './permission/permission.component';
import { IsystemLogComponent } from './log/log.component';
import { IsystemDictComponent } from './dict/dict.component';
import { IsystemAnnountCementComponent } from './annount-cement/annount-cement.component';
import { IsystemQuartzJobListComponent } from './quartz-job-list/quartz-job-list.component';
import { IsystemRoleApprComponent } from './role/role-appr/role-appr.component';
import { IsystemRoleAddComponent } from './role/role-add/role-add.component';
import { IsystemRoleEditComponent } from './role/role-edit/role-edit.component';
import { IsystemUserEditComponent } from './user/user-edit/user-edit.component';
import { IsystemUserPasswordUpdateComponent } from './user/user-password-update/user-password-update.component';
import { IsystemUserViewComponent } from './user/user-view/user-view.component';
import { IsystemDictEditComponent } from './dict/dict-edit/dict-edit.component';
import { IsystemDictAddComponent } from './dict/dict-add/dict-add.component';
import { IsystemDictItemComponent } from './dict/dict-item/dict-item.component';
import { IsystemDictItemAddComponent } from './dict/dict-item/dict-item-add/dict-item-add.component';
import { IsystemDictItemEditComponent } from './dict/dict-item/dict-item-edit/dict-item-edit.component';
import { IsystemAnnountAddComponent } from './annount-cement/annount-add/annount-add.component';
import { IsystemAnnountEditComponent } from './annount-cement/annount-edit/annount-edit.component';

const COMPONENTS = [
  IsystemUserComponent,
  IsystemRoleComponent,
  IsystemDepartComponent,
  IsystemPermissionComponent,
  IsystemLogComponent,
  IsystemDictComponent,
  IsystemAnnountCementComponent,
  IsystemQuartzJobListComponent];
const COMPONENTS_NOROUNT = [
  IsystemRoleApprComponent,
  IsystemRoleAddComponent,
  IsystemRoleEditComponent,
  IsystemUserEditComponent,
  IsystemUserPasswordUpdateComponent,
  IsystemUserViewComponent,
  IsystemDictEditComponent,
  IsystemDictAddComponent,
  IsystemDictItemComponent,
  IsystemDictItemAddComponent,
  IsystemDictItemEditComponent,
  IsystemAnnountAddComponent,
  IsystemAnnountEditComponent];

@NgModule({
  imports: [
    SharedModule,
    IsystemRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class IsystemModule { }
