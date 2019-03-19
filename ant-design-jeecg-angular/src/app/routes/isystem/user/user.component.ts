import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { IsystemUserEditComponent } from './user-edit/user-edit.component';
import { NzMessageService } from 'ng-zorro-antd';
import { IsystemUserPasswordUpdateComponent } from './user-password-update/user-password-update.component';
import { IsystemUserViewComponent } from './user-view/user-view.component';
import { IsystemUserAddComponent } from './user-add/user-add.component';

@Component({
  selector: 'app-isystem-user',
  templateUrl: './user.component.html',
})
export class IsystemUserComponent implements OnInit {
  url = `sys/user/list`;
  searchSchema: SFSchema = {
    properties: {
      username: {
        type: 'string',
        title: '用户账号'
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '用户账号', index: 'username' },
    { title: '真实姓名', index: 'realname' },
    {
      title: '头像',
      type: 'img',
      width: '50px',
      index: 'avatar'
    },
    {
      title: '性别',
      index: 'sex_dictText',
    },
    { title: '生日', type: 'date', dateFormat: 'YYYY-MM-DD', index: 'birthday' },
    {
      title: '创建日期', type: 'date', index: 'createTime', sort: {
        key: 'order',
        reName: {
          ascend: 'asc',
          descend: 'desc'
        }
      }
    },
    { title: '手机号', index: 'phone' },
    {
      title: '操作',
      buttons: [
         {
          text: '编辑', icon: 'edit', type: 'modal',
          modal: {
            component: IsystemUserEditComponent,
          },
          click: (record: any, modal: any) => this.st.reload()
        },
        {
          text: '更多',
          children: [
            {
              text: `查看`,
              type: 'modal',
              modal: {
                component: IsystemUserViewComponent,
              },
            },
            {
              text: `修改密码`,
              type: 'modal',
              modal: {
                component: IsystemUserPasswordUpdateComponent,
              },
            },
            {
              text: `删除`,
              type: 'del',
              click: (record) => this.http.delete(`sys/user/delete?id=${record.id}`).subscribe(res =>this.st.reload())
            },
            {
              text: `冻结`,
              type: 'none',
              pop:true,
              popTitle:'确认冻结吗？',
              click: (record, modal, comp) => {
                this.http.post(`sys/user/frozenBatch`,{'ids':record.id,'status':2}).subscribe(res => {
                  this.st.reload()
                })
              }
            },
          ]
        },
      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper, private message: NzMessageService) { }

  ngOnInit() { }

  add() {
     this.modal
       .createStatic(IsystemUserAddComponent)
       .subscribe(() => this.st.reload());
  }

}
