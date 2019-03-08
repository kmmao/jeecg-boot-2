import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { UpLoadComponent } from '@shared';
@Component({
  selector: 'app-outdoor-report',
  templateUrl: './report.component.html',
})
export class OutdoorReportComponent implements OnInit {
  @ViewChild('f') f;
  @ViewChild('u1') u1: UpLoadComponent;
  i: any = {
    questionClassify: 'A01'
  };

  constructor(public http: _HttpClient) { }

  ngOnInit() {
  }

  submit(value: any) {
    console.log(this.f);
    console.log(this.u1.fileList);
  }
}
