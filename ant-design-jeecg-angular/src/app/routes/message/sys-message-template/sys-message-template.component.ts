import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent, STData } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { MessageMessageTemplateEditComponent } from './message-template-edit/message-template-edit.component';
import { MessageMessageTemplateAddComponent } from './message-template-add/message-template-add.component';

@Component({
  selector: 'app-message-sys-message-template',
  templateUrl: './sys-message-template.component.html',
})
export class MessageSysMessageTemplateComponent implements OnInit {
  url = `message/sysMessageTemplate/list`;
  searchSchema: SFSchema = {
    properties: {
      templateCode: {
        type: 'string',
        title: '模板CODE'
      },
      templateContent: {
        type: 'string',
        title: '模板内容'
      },
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '模板CODE', index: 'templateCode' },
    { title: '模板标题', index: 'templateName' },
    { title: '模板内容', index: 'templateContent' },
    { title: '模板类型', index: 'templateType' },
    {
      title: '',
      buttons: [
        { text: '查看', click: (item: any) => `/form/${item.id}` },
        {
          text: '编辑',
          type: 'modal',
          modal: {
            component: MessageMessageTemplateEditComponent
          },
          click: 'reload'
        },
        {
          text:'删除',
          type:'del',
          click:(item:STData)=>{
            this.http.delete(`message/sysMessageTemplate/delete?id=${item.id}`)
          }

        }
      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper) { }

  ngOnInit() { }

  add() {
     this.modal
       .createStatic(MessageMessageTemplateAddComponent, { })
       .subscribe(() => this.st.reload());
  }

}
