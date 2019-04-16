import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';

@Component({
  selector: 'app-message-sys-message',
  templateUrl: './sys-message.component.html',
})
export class MessageSysMessageComponent implements OnInit {
  url = `message/sysMessage/list`;
  searchSchema: SFSchema = {
    properties: {
      no: {
        type: 'string',
        title: '编号'
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '消息标题', index: 'esTitle' },
    { title: '发送内容', index: 'esContent' },
    { title: '接收人', index: 'esReceiver' },
    { title: '发送次数', index: 'esSendNum' },
    { title: '发送状态', index: 'esSendStatus' },
    { title: '发送时间', index: 'esSendTime' },
    { title: '发送方式', index: 'esType' },
   
    {
      title: '',
      buttons: [
        // { text: '查看', click: (item: any) => `/form/${item.id}` },
        // { text: '编辑', type: 'static', component: FormEditComponent, click: 'reload' },
      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper) { }

  ngOnInit() { }

  add() {
    // this.modal
    //   .createStatic(FormEditComponent, { i: { id: 0 } })
    //   .subscribe(() => this.st.reload());
  }

}
