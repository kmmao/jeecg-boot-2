import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent, STData, STColumnTag } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { IsystemQuartzJobEditComponent } from './quartz-job-edit/quartz-job-edit.component';
import { IsystemQuartzJobAddComponent } from './quartz-job-add/quartz-job-add.component';
import { NzMessageService } from 'ng-zorro-antd';
import { DictService } from '@shared';

const TAG: STColumnTag = {
  '0': { text: '已启动', color: 'green' },
  '-1': { text: '已暂停', color: 'red' },
};
@Component({
  selector: 'app-isystem-quartz-job-list',
  templateUrl: './quartz-job-list.component.html',
})
export class IsystemQuartzJobListComponent implements OnInit {
  url = `sys/quartzJob/list`;
  searchSchema: SFSchema = {
    properties: {
      jobClassName: {
        type: 'string',
        title: '类名'
      },
      status: {
        type: 'string',
        title: '状态',
        ui:{
          widget:'select',
          width:220,
          asyncData:()=>this.dictService.getDict("job_status")
        }
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '类名', index: 'jobClassName' },
    { title: 'cron表达式', index: 'cronExpression' },
    { title: '参数', index: 'parameter' },
    { title: '描述', index: 'description' },
    { title: '状态', index: 'status',type: 'tag', tag: TAG},
    {
      title: '操作',
      buttons: [
        {
          text: '停止', icon: 'edit', type: 'none',
          click: (record: any, modal: any) =>{
            this.http.post("sys/quartzJob/pause",record).subscribe(
              res=>this.st.reload())
          },
          pop:true,
          popTitle:'确认停止服务吗？',
          iif:(item:STData)=>  item.status===0?true:false
        },
        {
          text: '启动', icon: 'edit', type: 'none',
          pop:true,
          popTitle:'确认启动服务吗',
          click: (record: any, modal: any) =>{
            this.http.post("sys/quartzJob/resume",record).subscribe(res=>this.st.reload())
          },
          iif:(item:STData)=>  item.status===-1?true:false
        },
        {
          text: '编辑', icon: 'edit', type: 'modal',
          modal:{
            component: IsystemQuartzJobEditComponent,
          },
          click: (record: any, modal: any) => this.st.reload()
        },
        {
          text: '删除', icon: 'edit', type: 'del',
          click: (record: any, modal: any) =>{
            this.http.delete(`sys/quartzJob/delete?id=${record.id}`).subscribe(res=>this.st.reload())
          },
          iif:(item:STData)=>  item.status===-1?true:false
        },
      ]

    }
  ];

  constructor(private dictService:DictService,private http: _HttpClient, private modal: ModalHelper, private message: NzMessageService) { }

  ngOnInit() { }

  add() {
    this.modal
      .createStatic(IsystemQuartzJobAddComponent)
      .subscribe(() => this.st.reload());
  }

}
