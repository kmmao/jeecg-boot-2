import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-demo-flow-test',
  templateUrl: './flow-test.component.html',
  styles  : [
    `
      .gutter-box {
        padding: 5px 0;
      }
    `
  ]
})
export class DemoFlowTestComponent implements OnInit {
  method="get";
  url;
  resp;
  param:any={}

  constructor(private http: _HttpClient) { }

  ngOnInit() { }
  doit(){
  

    switch(this.method){
     
      case "get":
        this.http.get(`${this.url}&flowTestFlag`,this.param).subscribe(res=>this.resp=JSON.stringify(res))
        break;
      case "post":
        this.http.post(`${this.url}&flowTestFlag`,this.param).subscribe(res=>this.resp=JSON.stringify(res))
        break;
      case "put":
        this.http.put(`${this.url}&flowTestFlag`,this.param).subscribe(res=>this.resp=JSON.stringify(res))
        break;
      case "delete":
        this.http.delete(`${this.url}&flowTestFlag`,this.param).subscribe(res=>this.resp=JSON.stringify(res))
    }

  }


}
