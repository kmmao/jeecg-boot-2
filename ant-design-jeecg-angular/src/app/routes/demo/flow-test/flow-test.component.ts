import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd';

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
  param:any=''
  reqplaceholder="这里可以输入{\"a\":1,\"b\":2} ，为了防止恶意提交，长度为1000。"
  constructor(private http: _HttpClient,private message:NzMessageService) { }

  ngOnInit() { }
  doit(){
    if(this.param===''){
      this.param='{}';
    }
    console.log(this.method)
    try {
      switch(this.method){
        case "get":
          this.http.get(`${this.url}`,JSON.parse(this.param)).subscribe(res=>{
            this.resp=JSON.stringify(res)
          })
          break;
        case "post":
          this.http.post(`${this.url}`,JSON.parse(this.param)).subscribe(res=>this.resp=JSON.stringify(res))
          break;
        case "put":
          this.http.put(`${this.url}`,JSON.parse(this.param)).subscribe(res=>this.resp=JSON.stringify(res))
          break;
        case "delete":
          this.http.delete(`${this.url}`,JSON.parse(this.param)).subscribe(res=>this.resp=JSON.stringify(res))
      }
    } catch (error) {
      console.log(error)
        this.message.error("请求参数格式不正确")
    }
  

  }


}
