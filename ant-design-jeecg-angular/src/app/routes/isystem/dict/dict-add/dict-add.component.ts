import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';
import { DictService } from '@shared';

@Component({
  selector: 'app-isystem-dict-add',
  templateUrl: './dict-add.component.html',
})
export class IsystemDictAddComponent implements OnInit {
  i: any = {
    type: 0,
    delFlag: 1
  };
  schema: SFSchema = {
    properties: {
      dictName: { type: 'string', title: '字典名称' },
      dictCode: { type: 'string', title: '字典编号', maxLength: 32 },
      type: { type: 'integer', title: '类型', maxLength: 15 },
      description: { type: 'string', title: '字典描述', maxLength: 150 },

    },
    required: ['dictCode', 'dictName','type'],
  };
  ui: SFUISchema = {
    $type: {
      widget: 'select',

      asyncData: () => this.dictService.getDict("dict_type")
    }
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
    private dictService: DictService
  ) { }

  ngOnInit(): void {
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
