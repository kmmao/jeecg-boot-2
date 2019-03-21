import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { UpLoadComponent } from '@shared';
import { NzModalRef } from 'ng-zorro-antd';

@Component({
  selector: 'app-isystem-user-add',
  templateUrl: './user-add.component.html',
})
export class IsystemUserAddComponent implements OnInit {
  @ViewChild('f') f;
  @ViewChild('u1') u1: UpLoadComponent;
  i: any = {};
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
