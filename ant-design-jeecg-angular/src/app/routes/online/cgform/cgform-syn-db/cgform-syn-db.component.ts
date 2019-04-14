import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-online-cgform-syn-db',
  templateUrl: './cgform-syn-db.component.html',
})
export class OnlineCgformSynDbComponent implements OnInit {
  record: any = {};
  i: any;
  synMethod='normal'
  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
  ) {}

  ngOnInit(): void {
   this.i=this.record
   
  }

  save(value: any) {
    this.http.post(`online/cgform/api/doDbSynch/${this.i.id}/${value.synMethod}`).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }
}
