import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { NzMessageService } from 'ng-zorro-antd';
import { IsystemDictEditComponent } from './dict-edit/dict-edit.component';
import { IsystemDictItemComponent } from './dict-item/dict-item.component';
import { IsystemDictAddComponent } from './dict-add/dict-add.component';

@Component({
  selector: 'app-isystem-dict',
  templateUrl: './dict.component.html',
})
export class IsystemDictComponent implements OnInit {
  url = `/sys/ng-alain/treeList?delFlag=1`;
  searchSchema: SFSchema = {
    properties: {
      dictName: {
        type: 'string',
        title: '字典名称'
      },
      dictCode: {
        type: 'string',
        title: '字典编号'
      },
    }
  };
  
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [

    { title: '字典名称', index: 'dictName' },
    { title: '字典编号', index: 'dictCode' },
    { title: '描述', index: 'description' },
    {
      title: '',
      buttons: [
        {
          text: '编辑', icon: 'edit', type: 'modal',
          modal: {
            component: IsystemDictEditComponent,
          },
          click: (record: any, modal: any) => {
            this.message.success(
              `${JSON.stringify(modal)}`,
            )
            this.st.reload();
          }
        },
        {
          text: `字典编辑`,
          type: 'drawer',
          drawer: {
            title: '编辑',
            component: IsystemDictItemComponent,
          },
          click: (record: any, modal: any) =>
            this.message.success(
              `${JSON.stringify(modal)}`,
            ),
        },
        {
          text: `删除`,
          type: 'del',
          click: (record, modal, comp) => {
            this.http.delete(`sys/dict/delete?id=${record.id}`).subscribe(res => {
              this.message.success((res as any).message);
              this.st.reload()
            })
          }
        },
      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper, private message: NzMessageService) { }

  ngOnInit() {

  }

  add() {
    this.modal
    .createStatic(IsystemDictAddComponent)
    .subscribe(() => this.st.reload());
  }

}
