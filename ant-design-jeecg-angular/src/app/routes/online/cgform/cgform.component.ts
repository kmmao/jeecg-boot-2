import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent, STColumnBadge, STColumnTag } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { OnlineCgformAddComponent } from './cgform-add/cgform-add.component';


const TAG: STColumnTag = {
  Y: { text: '成功', color: 'green' },
  N: { text: '未同步', color: 'red' },
}
@Component({
  selector: 'app-online-cgform',
  templateUrl: './cgform.component.html',
})
export class OnlineCgformComponent implements OnInit {
  url = `online/cgform/head/list`;
  searchSchema: SFSchema = {
    properties: {
      no: {
        type: 'string',
        title: '编号'
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: 'id', type: 'checkbox', index: 'id' },
    { title: '#', type: 'no' },
    { title: '表类型', index: 'tableType' },
    { title: '表名', index: 'tableName' },
    { title: '表单分类', index: 'formCategory' },
    { title: '表描述', index: 'tableTxt' },
    { title: '版本', index: 'tableVersion' },
    { title: '同步数据库状态', index: 'isDbSynch', type: 'tag', tag: TAG },
    {
      title: '操作',
      buttons: [
        {
          text: '编辑', type: 'modal',
          modal: {
            component: OnlineCgformAddComponent,
            size: "xl"
          },
          click: 'reload'
        },
        {
          text: `功能测试`,
          type: 'link',
          click: (item) => `online/cgformList/${item.id}`
        },
        {
          text: '更多',
          children: [
            {
              text: `配置地址`,
              type: 'modal',
             /*  modal: {
                component: IsystemUserPasswordUpdateComponent,
              }, */
            },
            {
              text: `移除`,
              type: 'none',
              pop: true,
              popTitle: '确认移除吗？',
              click: (record, modal, comp) => {
                this.http.post(`sys/user/frozenBatch`, { 'ids': record.id, 'status': 2 }).subscribe(res => {
                  this.st.reload()
                })
              }
            },
            {
              text: `删除`,
              type: 'del',
              click: (record) => this.http.delete(`sys/user/delete?id=${record.id}`).subscribe(res => this.st.reload())
            },
           
          ]
        }
      ]}
      ];

      constructor(private http: _HttpClient, private modal: ModalHelper) { }

  ngOnInit() { }

  add() {
        this.modal
          .static(
            OnlineCgformAddComponent, {},
            'xl'
          )
          .subscribe(() => this.st.reload());
      }
    }
