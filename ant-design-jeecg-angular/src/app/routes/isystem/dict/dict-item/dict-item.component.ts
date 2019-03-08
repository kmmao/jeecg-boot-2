import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { NzModalRef, NzMessageService, NzDrawerRef } from 'ng-zorro-antd';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';
import { STComponent, STColumn } from '@delon/abc';
import { IsystemDictItemEditComponent } from './dict-item-edit/dict-item-edit.component';
import { IsystemDictItemAddComponent } from './dict-item-add/dict-item-add.component';

@Component({
  selector: 'app-isystem-dict-item',
  templateUrl: './dict-item.component.html',
})
export class IsystemDictItemComponent implements OnInit {
  record: any = {};
  url
 
  searchSchema: SFSchema = {
    properties: {
      itemText: {
        type: 'string',
        title: '键名'
      },
      itemValue: {
        type: 'string',
        title: '键值'
      },
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [

    { title: '键名', index: 'itemText' },
    { title: '键值', index: 'itemValue' },
    {
      title: '',
      buttons: [
        {
          text: '编辑', icon: 'edit', type: 'modal',
          modal: {
            component: IsystemDictItemEditComponent,
          },
          click: (record: any, modal: any) => {
            this.st.reload();
          }
        },
        {
          text: `删除`,
          type: 'del',
          click: (record, modal, comp) => {
            this.http.delete(`sys/dictItem/delete?id=${record.id}`).subscribe(res => {
              this.st.reload()
            })
          }
        }, 
      ]
    }
  ];
  






  constructor(
    private ref: NzDrawerRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
    private modal: ModalHelper, 
    private message:NzMessageService
  ) { }

  ngOnInit(): void {
    this.url=`sys/dictItem/list?dictId=${this.record.id}&delFlag=1&column=createTime&order=asc&field=id,,sortOrder,itemText,itemValue,description,status,createTime,action`;
  }
  add() {
    this.modal
    .createStatic(IsystemDictItemAddComponent,{i: {dictId:this.record.id}})
    .subscribe(() => this.st.reload());
  }
}
