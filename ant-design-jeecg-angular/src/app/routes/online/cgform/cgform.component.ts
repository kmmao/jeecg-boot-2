import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { OnlineCgformAddComponent } from './cgform-add/cgform-add.component';

@Component({
  selector: 'app-online-cgform',
  templateUrl: './cgform.component.html',
})
export class OnlineCgformComponent implements OnInit {
  url = `online/cgform/head/list`;
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
    { title: 'id', type: 'checkbox',index:'id' },
    { title: '#', type: 'no' },
    { title: '表类型',  index: 'tableType' },
    { title: '表名',  index: 'tableName' },
    { title: '表单分类',  index: 'formCategory' },
    { title: '表描述',  index: 'tableTxt' },
    { title: '版本',  index: 'tableVersion' },
    { title: '同步数据库状态',  index: 'isDbSynch' },
    {
      title: '操作',
      buttons: [
         { text: '查看', click: (item: any) => `/form/${item.id}` },
         { text: '编辑', type: 'modal',
         modal:{
          component: OnlineCgformAddComponent,
          size:"xl"
         },
           click: 'reload' },
      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper) { }

  ngOnInit() { }

  add() {
     this.modal
       .static(
         OnlineCgformAddComponent, {  },
          'xl'
         )
       .subscribe(() => this.st.reload());
  }
}
