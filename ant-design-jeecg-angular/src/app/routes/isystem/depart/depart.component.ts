import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema, SFUISchema } from '@delon/form';
import { NzTreeComponent } from 'ng-zorro-antd';
import { IsystemDepartAddComponent } from './depart-add/depart-add.component';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'app-isystem-depart',
  templateUrl: './depart.component.html',
  styles  : [
    `
    [nz-button] {
      margin-right: 8px;
      margin-bottom: 12px;
    }
    nz-input-group {
      padding: 10px 0;
    }
  `
]
})
export class IsystemDepartComponent implements OnInit {

  nodes=[];
  
  i: any={};
  @ViewChild('treeCom') treeCom: NzTreeComponent;
  searchValue;
  schema: SFSchema = {
    properties: {
      departName: { type: 'string', title: '部门名称' },
      parentDepartName: { type: 'string', title: '父级部门名称' ,readOnly:true},
      departOrder: { type: 'number', title: '序号' },
      fax: { type: 'string', title: '传真' },
      address: { type: 'string', title: '地址' },
      description: { type: 'string', title: '描述' },

    },
    required: ['departName'],
  };
  ui: SFUISchema = {

  };


  constructor(private http: _HttpClient, private modal: ModalHelper,private message:NzMessageService) { }

  ngOnInit() {
    this.getDeparts()
   }

   
   getDeparts(){
    this.http.get('sysdepart/sysDepart/queryTreeList').subscribe(res=>{
      this.nodes=(res as any).result
    });
   }

   nzEvent(event){
     this.i=event.node.origin
     this.i['parentDepartName']=event.node.parentNode.origin.departName
     this.i['parentId']=event.node.parentNode.origin.id
      console.log(event)
   }
   nzSearchValueChange(event){
      console.log(event)
   }
  add() {
    this.modal
      .createStatic(IsystemDepartAddComponent,{ i: { parentName:`一级菜单`} })
      .subscribe(() => this.getDeparts());
  }
  addsub() {
    console.log(this.i.key)
    if(!this.i.key){
      this.message.error('请选择一个部门')
      return;
    }
    this.modal
      .createStatic(IsystemDepartAddComponent, { i: { parentId: this.i.key,parentName:this.i.parentDepartName} })
      .subscribe(() => this.getDeparts());
  }
  save(value){
    this.http.put(`sysdepart/sysDepart/edit`,value).subscribe(res=>{
      this.message.info((res as any).message)
      this.getDeparts()
    })
  }

}
