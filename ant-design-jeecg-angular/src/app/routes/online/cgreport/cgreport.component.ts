import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { OnlineCgreportEditComponent } from './cgreport-edit/cgreport-edit.component';
import { OnlineCgreportAddComponent } from './cgreport-add/cgreport-add.component';

@Component({
  selector: 'app-online-cgreport',
  templateUrl: './cgreport.component.html',
})
export class OnlineCgreportComponent implements OnInit {
  url = `online/cgreport/head/list`;
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
    { title: 'id', type:'checkbox', index: 'id' },
    { title: '报表名称', index: 'name' },
    { title: '编码', index: 'code' },
    { title: '查询SQL', index: 'cgrSql' },
    { title: '数据源', index: 'dbSource' },
    { title: '创建时间',type:'date' ,index: 'createTime' },
    { title: '描述', index: 'content' },
    {
      title: '操作',
      buttons: [
         { text: '查看', click: (item: any) => `/form/${item.id}` },
         { text: '编辑', type: 'modal',
         modal:{
          component: OnlineCgreportEditComponent,
          size:"xl"
         },
           click: 'reload' }      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper) { }

  ngOnInit() { }

  add() {
    this.modal
      .static(OnlineCgreportAddComponent, { },'xl')
      .subscribe(() => this.st.reload());
  }

}
