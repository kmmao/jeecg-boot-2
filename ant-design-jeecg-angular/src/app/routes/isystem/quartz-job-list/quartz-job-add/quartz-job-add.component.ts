import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-quartz-job-add',
  templateUrl: './quartz-job-add.component.html',
})
export class IsystemQuartzJobAddComponent implements OnInit {
  i: any={};
  schema: SFSchema = {
    properties: {
      jobClassName: { type: 'string', title: '类名' },
      cronExpression: { type: 'string', title: 'cron表达式' },
      parameter: { type: 'string', title: '参数' },
      description: { type: 'string', title: '描述' },
      status: {
        type: 'string',
        title: '状态',
        enum: [
          { label: '开启', value: '0' },
          { label: '关闭', value: '-1' },
        ],
        default:'-1'
      },
    },
    required: ['jobClassName', 'cronExpression', 'status'],
  };
  ui: SFUISchema = {

    $status: {
      widget: 'radio',
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
    this.http.post(`sys/quartzJob/add`, value).subscribe(res => {
      this.close()
    });
  }

  close() {
    this.modal.destroy();
  }
}
