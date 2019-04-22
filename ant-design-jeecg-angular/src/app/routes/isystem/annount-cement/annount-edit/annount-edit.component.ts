import { Component, Input, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { NzModalRef } from 'ng-zorro-antd';
import { isDate } from 'util';
import {DatePipe} from "@angular/common"; 
@Component({
  selector: 'app-isystem-annount-edit',
  templateUrl: './annount-edit.component.html',
})
export class IsystemAnnountEditComponent implements OnInit {
  record: any = {};
  i: any;
  
  constructor(
    private modal: NzModalRef,
    public http: _HttpClient,
    private datePipe:DatePipe
  ) { }

  ngOnInit(): void {
    this.i=this.record;
  }

  save(value: any) {
    for (const key in this.i) {
      if(isDate(this.i[key])){
        this.i[key]=this.datePipe.transform(this.i[key],"yyyy-MM-dd HH:mm:ss")
        }
    } 
    console.log(this.i)
    this.http.put(`sys/annountCement/edit`, this.i).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }
}
