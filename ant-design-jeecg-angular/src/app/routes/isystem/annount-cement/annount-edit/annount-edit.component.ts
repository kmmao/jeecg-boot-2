import { Component, Input, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { NzModalRef } from 'ng-zorro-antd';

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
  ) { }

  ngOnInit(): void {
    this.i=this.record;
  }

  save(value: any) {
    this.http.put(`sys/annountCement/edit`, this.i).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }
}
