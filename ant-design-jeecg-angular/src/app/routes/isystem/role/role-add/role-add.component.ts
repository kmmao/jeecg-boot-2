import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-role-add',
  templateUrl: './role-add.component.html',
})
export class IsystemRoleAddComponent implements OnInit {
  record: any = {};
  i: any = {};
  schema: SFSchema = {
    properties: {
      roleName: { type: 'string', title: '角色名称' },
      roleCode: { type: 'string', title: '角色编码', maxLength: 15 },
      description: {
        type: 'string',
        title: '描述',
        ui: {
          widget: 'textarea',
          autosize: { minRows: 8, maxRows: 12 }
        }
      },
    },
    required: ['roleName','roleCode'],
  };


  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
  ) { }

  ngOnInit(): void {

    this.i = this.record;
    console.log(this.record)
    /* if (this.record.id > 0)
    this.http.get(`/user/${this.record.id}`).subscribe(res => (this.i = res)); */
  }

  save(value: any) {
    this.http.post(`sys/role/add`, value).subscribe(res => {
      this.modal.close((res as any).message);
    });
  }

  close() {
    this.modal.destroy();
  }
}
