import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema, SFSchemaEnumType } from '@delon/form';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-isystem-user-edit',
  templateUrl: './user-edit.component.html',
})
export class IsystemUserEditComponent implements OnInit {
  @Input()
  record: any = {};
  i: any;
  schema: SFSchema = {
    properties: {
      username: { type: 'string', title: '用户名' },
      realname: { type: 'string', title: '用户姓名' },
      selectedroles: { type: 'string', title: '用户角色' ,
      enum: [
        { label: '人力资源部', value: 'hr' },
        { label: '临时角色', value: 'test' },
        { label: '管理员', value: 'admin' }
    ] },
      avatar: { type: 'string', title: '头像' },
      birthday: { type: 'string', title: '生日' },
      sex: {
       type: 'number',
       title: '性别',
       enum: [
        { label: '男', value: 1 },
        { label: '女', value: 2 },
    ] 
     },
      email: { type: 'string', title: '邮箱' },
      phone: { type: 'string', title: '电话' },
    },
    required: ['username', 'realname', 'selectedroles'],
  };
  ui: SFUISchema = {
    '*': {
      spanLabelFixed: 100,
      grid: { span: 12 },
    },
    $selectedroles: {
      widget: 'select',
      mode: 'tags',
    },
    $birthday: {
      widget: 'date',
    },
    $sex: {
      widget: 'select',
    },
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
    this.http.put(`sys/user/edit`, value).subscribe(res => {
      this.modal.close((res as any).message);
    });
  }

  close() {
    this.modal.destroy();
  }
  getRoles():Observable<any> {
    return this.http.get('sys/role/queryall');
  }
}
