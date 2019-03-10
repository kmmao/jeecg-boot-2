import { Component, Input, OnInit } from '@angular/core';
import { SFSchema, SFUISchema } from '@delon/form';
import { _HttpClient } from '@delon/theme';
import { NzMessageService, NzModalRef } from 'ng-zorro-antd';
import { Observable } from 'rxjs';
import { DictService } from '@shared';

@Component({
  selector: 'app-isystem-user-edit',
  templateUrl: './user-edit.component.html',
})
export class IsystemUserEditComponent implements OnInit {
  @Input()
  record: any = {};
  i: any={};
  schema: SFSchema = {
    properties: {
      username: { type: 'string', title: '用户名' },
      realname: { type: 'string', title: '用户姓名' },
      selectedroles: {
        type: 'string', title: '用户角色'
      },
      avatar: { type: 'string', title: '头像' },
      birthday: { type: 'string', title: '生日' },
      sex: {
        type: 'integer',
        title: '性别',
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
      asyncData: () => this.dictService.getDictByTable('sys_role','role_name','id')
    },
    $birthday: {
      widget: 'date',
    },
    $sex: {
      widget: 'select',
      asyncData:()=>this.dictService.getDict('sex')
    },
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
    private dictService:DictService
  ) { }

  ngOnInit(): void {
   this.http.get(`sys/user/queryUserRole?userid=${this.record.id}`).subscribe(res=>{
    this.i = this.record;
    this.i['selectedroles']=(res as any).result
    console.log(this.i)
   })
  }

  save(value: any) {
    value['selectedroles'] = value.selectedroles.join(",");
    this.http.put(`sys/user/edit`, value).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }

}
