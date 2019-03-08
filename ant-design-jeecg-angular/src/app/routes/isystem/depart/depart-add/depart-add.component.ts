import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-depart-add',
  templateUrl: './depart-add.component.html',
})
export class IsystemDepartAddComponent implements OnInit {
  i: any;
  schema: SFSchema = {
    properties: {
      departName: { type: 'string', title: '机构名称' },
      parentName: { type: 'string', title: '上级部门',readOnly:true },
      mobile: { type: 'string', title: '电话', },
      fax: { type: 'string', title: '传真', },
      address: { type: 'string', title: '地址', },
      departOrder: { type: 'number', title: '排序' },
      memo: { type: 'string', title: '备注', },
    },
    required: ['departName', 'mobile'],
  };
  ui: SFUISchema = {
   
   
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
  ) {}

  ngOnInit(): void {
    console.log(this.i)
  }

  save(value: any) {
    this.http.post(`sysdepart/sysDepart/add`, value).subscribe(res => {
      this.modal.close((res as any).message);
    });
  }
  close() {
    this.modal.destroy();
  }
}
