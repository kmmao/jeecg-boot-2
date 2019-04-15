import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { OnlineRoutingModule } from './online-routing.module';
import { OnlineCgformComponent } from './cgform/cgform.component';
import { OnlineCgformEditComponent } from './cgform/cgform-edit/cgform-edit.component';
import { OnlineCgformAddComponent } from './cgform/cgform-add/cgform-add.component';
import { OnlineCgreportComponent } from './cgreport/cgreport.component';
import { OnlineCgreportAddComponent } from './cgreport/cgreport-add/cgreport-add.component';
import { OnlineCgreportEditComponent } from './cgreport/cgreport-edit/cgreport-edit.component';
import { OnlineCgformListComponent } from './cgform-list/cgform-list.component';
import { OnlineCgformListAddComponent } from './cgform-list/cgform-list-add/cgform-list-add.component';
import { OnlineCgformListEditComponent } from './cgform-list/cgform-list-edit/cgform-list-edit.component';
import { OnlineCgformSynDbComponent } from './cgform/cgform-syn-db/cgform-syn-db.component';

const COMPONENTS = [
  OnlineCgformComponent,
  OnlineCgreportComponent,
  OnlineCgformListComponent];
const COMPONENTS_NOROUNT = [
  OnlineCgformEditComponent,
  OnlineCgformAddComponent,
  OnlineCgreportAddComponent,
  OnlineCgreportEditComponent,
  OnlineCgformListAddComponent,
  OnlineCgformListEditComponent,
  OnlineCgformSynDbComponent];

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
