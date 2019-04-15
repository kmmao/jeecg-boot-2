import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent, STColumnBadge, STColumnTag, STData } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { OnlineCgformAddComponent } from './cgform-add/cgform-add.component';
import { OnlineCgformEditComponent } from './cgform-edit/cgform-edit.component';
import { OnlineCgformSynDbComponent } from './cgform-syn-db/cgform-syn-db.component';
import { CacheService } from '@delon/cache';


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
            component: OnlineCgformEditComponent,
            size: "xl"
          },
          click: (record:STData)=>{
              this.st.reload();
              this.cacheService.remove(`online/cgform/api/getFormItem/${record.id}`)
              this.cacheService.remove(`online/cgform/api/getColumns/${record.id}`)
          }
        },
        {
          text: `功能测试`,
          type: 'link',
          click: (item) => `online/cgformList/${item.id}`,
          iif:(item:STData)=>{
            return item.isDbSynch==='Y'?true:false
           }
        },
        {
          text: `同步数据库`,
          type: 'modal',
          modal:{
              component:OnlineCgformSynDbComponent,
              size:'md'
          },
          click: () => this.st.reload(),
          iif:(item:STData)=>{
            return item.isDbSynch==='N'?true:false
           }
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
              iif:(item:STData)=>{
               return item.isDbSynch==='Y'?true:false
              }
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

      constructor(private http: _HttpClient, private modal: ModalHelper, private cacheService: CacheService ) { }

  ngOnInit() { 
    
  }

  add() {
        this.modal
          .static(
            OnlineCgformAddComponent, {},
            'xl'
          )
          .subscribe(() => this.st.reload());
      }
    }
