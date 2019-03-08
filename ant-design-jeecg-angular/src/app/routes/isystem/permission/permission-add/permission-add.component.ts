import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-permission-add',
  templateUrl: './permission-add.component.html',
})
export class IsystemPermissionAddComponent implements OnInit {
  record: any = {};
  i: any;
  schema: SFSchema = {
    properties: {
      name: { type: 'string', title: '菜单名称' },
      url: { type: 'string', title: '菜单路径'},
      component: { type: 'string', title: '前端组件'},
      redirect: { type: 'string', title: '默认跳转地址'},
      icon: { type: 'string', title: '菜单图标' },
      sortNo: { type: 'number', title: '排序' },
      hidden: { type: 'boolean', title: '隐藏路由', },
      alwaysShow: { type: 'boolean', title: '聚合路由', },
    },
    required: ['name'],
  };
  ui: SFUISchema = {
   
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
  ) {}

  ngOnInit(): void {
    if (this.record.id > 0)
    this.http.get(`/user/${this.record.id}`).subscribe(res => (this.i = res));
  }

  save(value: any) {
    this.http.post(`/user/${this.record.id}`, value).subscribe(res => {
      this.msgSrv.success('保存成功');
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }
}
