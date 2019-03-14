import { Component, OnInit, ViewChild } from '@angular/core';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { ModalHelper, _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd';
import { IsystemRoleApprComponent } from './role-appr/role-appr.component';
import { IsystemRoleEditComponent } from './role-edit/role-edit.component';
import { IsystemRoleAddComponent } from './role-add/role-add.component';
import { DeleteFill } from '@ant-design/icons-angular/icons/public_api';

@Component({
  selector: 'app-isystem-role',
  templateUrl: './role.component.html',
})
export class IsystemRoleComponent implements OnInit {
  url = `sys/role/list`;
  searchSchema: SFSchema = {
    properties: {
      roleName: {
        type: 'string',
        title: '角色名称'
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
    { title: '', index: 'id', type: 'checkbox' },
    { title: '#', type: 'no' },
    { title: '角色名称', index: 'roleName' },
    { title: '角色编码', index: 'roleCode' },
    { title: '备注', index: 'description' },
    { title: '创建时间', type: 'date', index: 'createTime' },
    { title: '更新时间', type: 'date', index: 'updateTime' },
    {
      title: '操作',
      buttons: [
        {
          text: '编辑', icon: 'edit', type: 'modal',
          modal: {
            component: IsystemRoleEditComponent,
          },
          click: (record: any, modal: any) => {
            this.st.reload();
          }
        },
        {
          text: '更多',
          children: [
            {
              text: `授权`,
              type: 'drawer',
              drawer: {
                title: '编辑',
                component: IsystemRoleApprComponent,
              },
            },
            {
              text: `删除`,
              type: 'del',
              click: (record, modal, comp) => {
                this.http.delete(`sys/role/delete?id=${record.id}`).subscribe(res => {
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
      .createStatic(IsystemRoleAddComponent)
      .subscribe(() => this.st.reload());
  }
}
