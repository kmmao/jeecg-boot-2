import { Component, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { NzModalRef, NzMessageService, NzInputDirective } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';


 interface Data {
  id: string;
  name: string;
  age: string;
  address: string;
  disabled: boolean;
}

@Component({
  selector: 'app-online-cgform-add',
  templateUrl: './cgform-add.component.html',
  styles: [
    `
    [nz-button] {
      margin-right: 8px;
      margin-bottom: 12px;
    }
  `
  ]
})
export class OnlineCgformAddComponent implements OnInit {
  @ViewChild('f') f;
  i: any = {
    tableType:1,
    formCategory:'bdfl_include',
    idType:'UUID',
    formTemplate:'ledefault',
    formTemplateMobile:'ledefault',
    queryMode:'single',
    isCheckbox:'N',
    isPage:'N',
    isTree:'N',

  };
  indexs=[];
  items=[
    {
      dbFieldName: 'id',
      dbFieldTxt: '主键',
      dbLength: 36,
      dbPointLength: 0,
      dbDefaultVal: '',
      dbType: 'string',
      dbIsKey: true,
      dbIsNull: false,
      disabled:true,
      fieldLength:120,
      fieldShowType:'text',
      queryMode:'single'
    },
    {
      dbFieldName: 'create_by',
      dbFieldTxt: '创建人登录名称',
      dbLength: 50,
      dbPointLength: 0,
      dbDefaultVal: '',
      dbType: 'string',
      dbIsKey: false,
      dbIsNull: true,
      fieldLength:120,
      fieldShowType:'text',
      queryMode:'single'

    },
    {
      dbFieldName: 'create_time',
      dbFieldTxt: '创建日期',
      dbLength: 20,
      dbPointLength: 0,
      dbDefaultVal: '',
      dbType: 'Date',
      dbIsKey: false,
      dbIsNull: true,
      fieldLength:120,
      fieldShowType:'text',
      queryMode:'single'

    },
    {
      dbFieldName: 'update_by',
      dbFieldTxt: '更新人登录名称',
      dbLength: 50,
      dbPointLength: 0,
      dbDefaultVal: '',
      dbType: 'string',
      dbIsKey: false,
      dbIsNull: true,
      fieldLength:120,
      fieldShowType:'text',
      queryMode:'single'

    },
    {
      dbFieldName: 'update_time',
      dbFieldTxt: '更新日期',
      dbLength: 20,
      dbPointLength: 0,
      dbDefaultVal: '',
      dbType: 'Date',
      dbIsKey: false,
      dbIsNull: true,
      fieldLength:120,
      fieldShowType:'text',
      queryMode:'single'

    },
  ];


  addRow(): void {
    this.items = [ ...this.items, {
      dbFieldName: '',
      dbFieldTxt: '',
      dbLength: 32,
      fieldLength:120,
      dbPointLength: 0,
      dbDefaultVal: '',
      dbType: 'string',
      dbIsKey: false,
      dbIsNull: true,
      fieldShowType:'text',
      queryMode:'single'

    } ];
  }
  
  addIndex(): void {
    this.indexs = [ ...this.indexs, {
      indexName: '',
      dbFieldName: '',
      indexType: 'normal',
    } ];
  }


  constructor(
    private modal: NzModalRef,
    public http: _HttpClient,
  ) { }

  ngOnInit(): void {

  }

  save(value: any) {
    
    this.http.post(`sys/user/add`, value).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }
 
}
