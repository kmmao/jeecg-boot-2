import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-role-edit',
  templateUrl: './role-edit.component.html',
})
export class IsystemRoleEditComponent implements OnInit {
  @Input()
  record: any = {};
  i: any = {};
  schema: SFSchema = {
    properties: {
      roleName: { type: 'string', title: '角色名称' },
      roleCode: { type: 'string', title: '角色编码', readOnly: true, maxLength: 15 },
      description: {
        type: 'string',
        title: '描述',
        ui: {
          widget: 'textarea',
          autosize: { minRows: 8, maxRows: 12 }
        }
      },
    },
    required: ['roleName'],
  };


  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
  ) { }

  ngOnInit(): void {
    this.i = this.record;
  }

  save(value: any) {
    this.http.put(`sys/role/edit`, value).subscribe(res => {
      this.modal.close((res as any).message);
    });
  }

  close() {
    this.modal.destroy();
  }
}
