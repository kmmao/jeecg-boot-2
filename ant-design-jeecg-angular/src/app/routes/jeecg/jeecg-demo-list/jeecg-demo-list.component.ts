import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { JeecgDemoListEditComponent } from './demo-list-edit/demo-list-edit.component';
import { JeecgDemoListAddComponent } from './demo-list-add/demo-list-add.component';

@Component({
  selector: 'app-jeecg-jeecg-demo-list',
  templateUrl: './jeecg-demo-list.component.html',
})
export class JeecgJeecgDemoListComponent implements OnInit {
  url = `test/jeecgDemo/list?field=id,name,keyWord,punchTime,sex,age,birthday,email,content,act`;
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
    { title: 'id', type: 'checkbox',index:"id"},
    { title: '#', type: 'no' },
    { title: '姓名',  index: 'name' },
    { title: '关键词',index: 'keyWord' },
    { title: '打卡时间', type:"date", index: 'punchTime' },
    { title: '性别',  index: 'sex' },
    { title: '生日', type: 'date', index: 'birthday' },
    { title: '个人简介',  index: 'content' },
    {
      title: '操作',
      buttons: [
         { text: '查看', click: (item: any) => `/form/${item.id}` },
         { text: '编辑', type: 'modal', component: JeecgDemoListEditComponent, click: 'reload' },
      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper) { }

  ngOnInit() { }

  add() {
    this.modal
      .createStatic(JeecgDemoListAddComponent, { i: { id: 0 } })
      .subscribe(() => this.st.reload());
  }

}
