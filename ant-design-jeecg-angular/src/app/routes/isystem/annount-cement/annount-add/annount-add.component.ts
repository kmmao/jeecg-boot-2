import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-annount-add',
  templateUrl: './annount-add.component.html',
})
export class IsystemAnnountAddComponent implements OnInit {
  record: any = {};
  i: any;
  schema: SFSchema = {
    properties: {
      titile: { type: 'string', title: '标题' },
      msgContent: { type: 'string', title: '内容', maxLength: 150 },
      startTime: { type: 'string', title: '开始时间' },
      endTime: { type: 'string', title: '结束时间', },
      priority: {
        type: 'string',
        title: '优先级',
        enum: [
          { label: '低', value: 'L' },
          { label: '中', value: 'M' },
          { label: '高', value: 'H' }
        ],
      },
      msgType: {
        type: 'string',
        title: '公告对象类型',
        enum: [
          { label: '全体用户', value: 'ALL' },
          { label: '指定用户', value: 'M' },
      ],
      },
    },
    required: ['titile', 'msgContent', 'startTime', 'endTime', 'priority', 'msgType'],
  };
  ui: SFUISchema = {
    $startTime: {
      widget: 'date'
    },
    $endTime: {
      widget: 'date',
    },
    $msgContent: {
      widget: 'textarea',
      grid: { span: 24 },
    },
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
  ) { }

  ngOnInit(): void {
    
  }

  save(value: any) {
    this.http.post(`sys/annountCement/add`, value).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }
}
