import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { JeecgRoutingModule } from './jeecg-routing.module';
import { JeecgJeecgDemoListComponent } from './jeecg-demo-list/jeecg-demo-list.component';
import { JeecgDemoListAddComponent } from './jeecg-demo-list/demo-list-add/demo-list-add.component';
import { JeecgDemoListEditComponent } from './jeecg-demo-list/demo-list-edit/demo-list-edit.component';

const COMPONENTS = [
  JeecgJeecgDemoListComponent];
const COMPONENTS_NOROUNT = [
  JeecgDemoListAddComponent,
  JeecgDemoListEditComponent];

@NgModule({
  imports: [
    SharedModule,
    JeecgRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class JeecgModule { }
