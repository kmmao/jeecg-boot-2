import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { SFSchema, SFUISchema } from '@delon/form';
import { _HttpClient } from '@delon/theme';
import { DictService, UpLoadComponent } from '@shared';
import { NzMessageService, NzModalRef } from 'ng-zorro-antd';

@Component({
  selector: 'app-isystem-user-edit',
  templateUrl: './user-edit.component.html',
})
export class IsystemUserEditComponent implements OnInit {
  @ViewChild('f') f;
  @ViewChild('u1') u1: UpLoadComponent;
  @Input()
  record: any = {};
  i: any={};
  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
    private dictService:DictService
  ) { }

  ngOnInit(): void {
    this.i = this.record;
   this.http.get(`sys/user/queryUserRole?userid=${this.record.id}`).subscribe(res=>{
    this.i['selectedroles']=(res as any).result
   })
  }

  save(value: any) {
    if (this.u1.fileList[0]) {
      console.log(this.u1.fileList[0])
      this.i['avatar'] = this.u1.fileList[0].response.message
    }
    this.i['selectedroles'] = this.i.selectedroles.join(",");
    this.http.put(`sys/user/edit`, this.i).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }

}
