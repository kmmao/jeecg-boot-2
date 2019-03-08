import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema, FormProperty, PropertyGroup } from '@delon/form';

@Component({
  selector: 'app-isystem-user-password-update',
  templateUrl: './user-password-update.component.html',
})
export class IsystemUserPasswordUpdateComponent implements OnInit {
  @Input()
  record: any = {};
  i: any={
    password:'',
  };
  schema: SFSchema = {
    properties: {
      username: { type: 'string', title: '用户名', },
      password: { type: 'string', title: '密码', maxLength: 15 },
      confirmpassword: { type: 'string', title: '确认密码' },
    },
    required: ['username', 'password', 'confirmpassword'],
  };
  ui: SFUISchema = {
    
    $username: {
      widget: 'text',
      readOnly:true
    },
    $password: {
    },
    $confirmpassword: {
      validator: (value: any, formProperty: FormProperty, form: PropertyGroup) => {
        if(form.value!=null){
          return form.value.password === value? [] : [{ keyword: 'required', message: '密码与确认密码不一致'}];
        }else{
          return [];
        }
      },
    },
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
  ) {}

  ngOnInit(): void {
    this.i['username']=this.record['username'];
  }

  save(value: any) {
    this.http.put(`sys/user/changPassword`, value).subscribe(res => {
      this.modal.close((res as any).message);
    });
  }

  close() {
    this.modal.destroy();
  }
}
