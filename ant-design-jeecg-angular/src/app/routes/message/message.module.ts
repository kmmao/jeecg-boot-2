import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { MessageRoutingModule } from './message-routing.module';
import { MessageSysMessageComponent } from './sys-message/sys-message.component';
import { MessageSysMessageTemplateComponent } from './sys-message-template/sys-message-template.component';
import { MessageMessageTemplateAddComponent } from './sys-message-template/message-template-add/message-template-add.component';
import { MessageMessageTemplateEditComponent } from './sys-message-template/message-template-edit/message-template-edit.component';

const COMPONENTS = [
  MessageSysMessageComponent,
  MessageSysMessageTemplateComponent];
const COMPONENTS_NOROUNT = [
  MessageMessageTemplateAddComponent,
  MessageMessageTemplateEditComponent];

@NgModule({
  imports: [
    SharedModule,
    MessageRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class MessageModule { }
