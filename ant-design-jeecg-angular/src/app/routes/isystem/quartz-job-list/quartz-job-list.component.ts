import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent, STData } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { IsystemQuartzJobEditComponent } from './quartz-job-edit/quartz-job-edit.component';
import { IsystemQuartzJobAddComponent } from './quartz-job-add/quartz-job-add.component';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'app-isystem-quartz-job-list',
  templateUrl: './quartz-job-list.component.html',
})
export class IsystemQuartzJobListComponent implements OnInit {
  url = `sys/quartzJob/list?field=id,jobClassName,cronExpression,parameter,description,status,action`;
  searchSchema: SFSchema = {
    properties: {
      jobClassName: {
        type: 'string',
        title: '类名'
      },
      status: {
        type: 'string',
        title: '状态'
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '类名', index: 'jobClassName' },
    { title: 'cron表达式', index: 'cronExpression' },
    { title: '参数', index: 'parameter' },
    { title: '描述', index: 'description' },
    { title: '状态', index: 'status',format:this.getDict},
    {
      title: '操作',
      buttons: [
        {
          text: '编辑', icon: 'edit', type: 'modal',
          modal:{
            component: IsystemQuartzJobEditComponent,
          },
          click: (record: any, modal: any) => this.st.reload()
        }
      ]

    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper, private message: NzMessageService) { }

  ngOnInit() { }

  add() {
    this.modal
      .createStatic(IsystemQuartzJobAddComponent)
      .subscribe(() => this.st.reload());
  }
  getDict(item: STData, col: STColumn):string{
    const dict={
      status:{
        '0':'正常',
        '-1':'停止',
      },
    }
    
    return dict[col.indexKey][item[col.indexKey]]
  }

}
