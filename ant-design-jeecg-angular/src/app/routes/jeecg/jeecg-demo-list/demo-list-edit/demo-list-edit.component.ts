import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';
import { DictService } from '@shared';

@Component({
  selector: 'app-jeecg-demo-list-edit',
  templateUrl: './demo-list-edit.component.html',
})
export class JeecgDemoListEditComponent implements OnInit {
  @Input()
  record: any = {};
  i: any;
  schema: SFSchema = {
    properties: {
      name: { type: 'string', title: '姓名' },
      keyWord: { type: 'string', title: '关键字', maxLength: 15 },
      punchTime: { type: 'string', title: '打卡时间' },
      sex: { type: 'string', title: '性别', format: 'uri' },
      birthday: { type: 'string', title: '描述', maxLength: 140 },
      content: { type: 'string', title: '个人简介', maxLength: 140 },
    },
    required: ['owner', 'callNo', 'href', 'description'],
  };
  ui: SFUISchema = {
    $punchTime: {
      widget: 'date'
    },
    $birthday: {
      widget: 'date'
    },
    $sex: {
      widget: 'select',
      asyncData: ()=>this.dictservice.getDict('sex'),
    },
    $content: {
      widget: 'textarea',
      autosize:{ minRows: 4, maxRows: 6 }
    },
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
    private dictservice:DictService
  ) {}

  ngOnInit(): void {
    this.i=this.record
  }

  save(value: any) {
    this.http.post(`/user/${this.record.id}`, value).subscribe(res => {
      this.msgSrv.success('保存成功');
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }
}
