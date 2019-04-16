import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MessageSysMessageComponent } from './sys-message/sys-message.component';
import { MessageSysMessageTemplateComponent } from './sys-message-template/sys-message-template.component';

const routes: Routes = [

  { path: 'sysMessageList', component: MessageSysMessageComponent },
  { path: 'sysMessageTemplateList', component: MessageSysMessageTemplateComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MessageRoutingModule { }
