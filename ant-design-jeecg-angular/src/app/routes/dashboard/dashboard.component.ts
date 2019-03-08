import { Component, OnInit, ElementRef } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {
  salesData: any[] = new Array(12).fill({}).map((i, idx) => ({
    x: `${idx + 1}月`,
    y: Math.floor(Math.random() * 1000) + 200,
  }));
  tb01=[];
  tb02=[];
  tb03=[];
  constructor(
    private http: _HttpClient
  ) { }
  
  ngOnInit() {
  /*   this.http.get(`standingBookInfoController.do?tb01&reportType=pie`).subscribe((res:any) => {
      res[0].data.forEach(item => {
          this.tb01.push({
            x: item.name,
            y: item.num,
          })
      });
    }); */
  /*   this.http.get(`standingBookInfoController.do?tb02&reportType=pie`).subscribe((res:any) => {
      res[0].data.forEach(item => {
          this.tb02.push({
            x: item.name,
            y: item.num,
          })
      });
    }); */
  }

}
