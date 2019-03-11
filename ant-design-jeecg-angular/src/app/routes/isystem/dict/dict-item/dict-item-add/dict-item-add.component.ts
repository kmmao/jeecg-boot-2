import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-dict-item-add',
  templateUrl: './dict-item-add.component.html',
})
export class IsystemDictItemAddComponent implements OnInit {
  
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
        default:1
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
    console.log(this.i);
  }

  save(value: any) {
    this.http.post(`sys/dictItem/add`, value).subscribe(res => {
      this.modal.close((res as any).message);
    });
  }

  close() {
    this.modal.destroy();
  }
}
