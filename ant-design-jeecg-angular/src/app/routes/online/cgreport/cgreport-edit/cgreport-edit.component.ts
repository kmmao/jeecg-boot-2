import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-online-cgreport-edit',
  templateUrl: './cgreport-edit.component.html',
})
export class OnlineCgreportEditComponent implements OnInit {
  @Input()
  record: any = {};
  @ViewChild('f') f;
  i: any = {};
  indexs = [];
  items = [];
  abc = "${abc}"
  sqlDesc = `您可以键入“”作为一个参数，这里abc是参数的名称。例如：
  select * from table where id = ${this.abc}。
  select * from table where id like concat('%',${this.abc},'%')。(mysql模糊查询)
  select * from table where id like '%'||${this.abc}||'%'。(oracle模糊查询)
  select * from table where id like '%'+${this.abc}+'%'。(sqlserver模糊查询)
  注：参数只支持动态报表，popup暂不支持`


  addRow(): void {
    this.items = [...this.items, {

    }];
  }

  addIndex(): void {
    this.indexs = [...this.indexs, {
      indexName: '',
      dbFieldName: '',
      indexType: 'normal',
    }];
  }


  constructor(
    private modal: NzModalRef,
    public http: _HttpClient,
  ) { }

  ngOnInit(): void {

  }
  checkAll(event) {
    console.log(event)
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
