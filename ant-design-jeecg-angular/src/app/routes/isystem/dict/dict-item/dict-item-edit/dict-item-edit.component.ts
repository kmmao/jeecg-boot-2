import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-dict-item-edit',
  templateUrl: './dict-item-edit.component.html',
})
export class IsystemDictItemEditComponent implements OnInit {
  record: any = {};
  i: any;
  schema: SFSchema = {
    properties: {
      itemText: { type: 'string', title: '键名' },
      itemValue: { type: 'string', title: '键值' },
      description: { type: 'string', title: '描述' },
      sortOrder: { type: 'number', title: '排序' },
      status: {
        type: 'string',
        title: '是否启用',
        enum: [
          { label: '启用', value: 1},
          { label: '未启用', value:0},
        ],
        ui: {
          widget: 'radio',
        },
      },

    },
    required: ['itemText', 'itemValue'],
  };
  ui: SFUISchema = {

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
    this.http.put(`sys/dictItem/edit`, value).subscribe(res => {
      this.modal.close((res as any).message);
    });
  }

  close() {
    this.modal.destroy();
  }
}
