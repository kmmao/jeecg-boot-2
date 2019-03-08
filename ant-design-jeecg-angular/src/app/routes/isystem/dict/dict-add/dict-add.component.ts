import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-dict-add',
  templateUrl: './dict-add.component.html',
})
export class IsystemDictAddComponent implements OnInit {
  record: any = {};
  i: any;
  schema: SFSchema = {
    properties: {
      dictName: { type: 'string', title: '字典名称' },
      dictCode: { type: 'string', title: '字典编号', maxLength: 15 },
      description: { type: 'string', title: '字典描述', maxLength: 150 },

    },
    required: ['dictCode', 'dictName'],
  };
  ui: SFUISchema = {
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
  ) {}

  ngOnInit(): void {
    this.i=this.record;
    this.i['delFlag']=1
  }

  save(value: any) {
    this.http.post(`sys/dict/add`, value).subscribe(res => {
      this.modal.close((res as any).message);
    });
  }

  close() {
    this.modal.destroy();
  }
  
}
