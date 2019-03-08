import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';

@Component({
  selector: 'app-isystem-log',
  templateUrl: './log.component.html',
})
export class IsystemLogComponent implements OnInit {
  url = `sys/log/list?logType=1&field=id,,,logContent,userid,username,ip,costTime,logType,createTime`;
  searchSchema: SFSchema = {
    properties: {
      keyWord: {
        type: 'string',
        title: '关键字'
      },
      createTime: {
        type: 'string',
        title: '创建时间',
        ui: { widget: 'date', mode: 'range' },
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '#', type: 'no' },
    { title: '日志内容', index: 'logContent' },
    { title: '操作人', index: 'userid' },
    { title: '操作人名称', index: 'username' },
    { title: 'IP', index: 'ip' },
    { title: '花费时间', index: 'costTime' },
    { title: '日志类型', index: 'logType' },
    { title: '创建时间', type: 'date', index: 'createTime' },
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
