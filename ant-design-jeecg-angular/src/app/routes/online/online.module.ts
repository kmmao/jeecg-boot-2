import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { OnlineRoutingModule } from './online-routing.module';
import { OnlineCgformComponent } from './cgform/cgform.component';
import { OnlineCgformEditComponent } from './cgform/cgform-edit/cgform-edit.component';
import { OnlineCgformAddComponent } from './cgform/cgform-add/cgform-add.component';

const COMPONENTS = [
  OnlineCgformComponent];
const COMPONENTS_NOROUNT = [
  OnlineCgformEditComponent,
  OnlineCgformAddComponent];

@NgModule({
  imports: [
    SharedModule,
    OnlineRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class OnlineModule { }
