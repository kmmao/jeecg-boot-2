import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-sys-role',
  templateUrl: './role.component.html',
})
export class SysRoleComponent implements OnInit {
  url = `/user`;
  public orbitUrl = 'roleController.do?role';
  searchSchema: SFSchema = {
    properties: {
      no: {
        type: 'string',
        title: '编号'
      },
      number:{
        type:'number',
        title:'调用次数'
      },
      updatedAt:{
        type:'string',
        format: 'date',
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    {title: 'id',
    index: 'id',
    type: 'checkbox',},
    { title: '编号', index: 'no' },
    { title: '调用次数', type: 'number', index: 'callNo' },
    { title: '头像', type: 'img', width: '50px', index: 'avatar' },
    { title: '时间', type: 'date', index: 'updatedAt' },
    {
      title: '操作',
      buttons: [
         { text: '查看', click: (item: any) => `/form/${item.id}` },
        //{ text: '编辑', type: 'static', component: FormEditComponent, click: 'reload' },
      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper) { }

  ngOnInit() { }
  change(event){
    console.log(event)
    if(event.type==='click'){
        event.click.item.checked===true?event.click.item.checked=false:event.click.item.checked=true;
    }
  }
  add() {
    // this.modal
    //   .createStatic(FormEditComponent, { i: { id: 0 } })
    //   .subscribe(() => this.st.reload());
  }

}
