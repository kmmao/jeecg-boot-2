import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-online-cgform-edit',
  templateUrl: './cgform-edit.component.html',
})
export class OnlineCgformEditComponent implements OnInit {
  record:any;
  @ViewChild('f') f;
  @ViewChild('dbTable') dbTable;
  i: any = {
    tableType:1,
    formCategory:'bdfl_include',
    idType:'UUID',
    formTemplate:'ledefault',
    formTemplateMobile:'ledefault',
    queryMode:'single',
    isCheckbox:'N',
    isPage:'N',
    isTree:'N',

  };
  indexs=[];
  items=[];


  addRow(): void {
    this.items = [ ...this.items, {
      dbFieldName: '',
      dbFieldTxt: '',
      dbLength: 32,
      fieldLength:120,
      dbPointLength: 0,
      dbDefaultVal: '',
      dbType: 'string',
      dbIsKey: false,
      dbIsNull: true,
      fieldShowType:'text',
      queryMode:'single',
      select:true
    } ];
  }
  deleteRow(){
    console.log(this.items)
    for (let index = 0; index < this.items.length; index++) {
      if(this.items[index].select){
        console.log(this.items[index])
        this.items.splice(index,index) 
      }
    }
    this.items=[...this.items]
    console.log(this.items)
  }
  
  addIndex(): void {
    this.indexs = [ ...this.indexs, {
      indexName: '',
      dbFieldName: '',
      indexType: 'normal',
    } ];
  }


  constructor(
    private modal: NzModalRef,
    public http: _HttpClient,
  ) { }

  ngOnInit(): void {
    this.i=this.record
    this.http.get(`online/cgform/field/listByHeadId?headId=${this.record.id}`).subscribe(res=>{
      this.items=(res as any).result.map((item: any) => {
        item['dbIsKey']=item['dbIsKey']===1?true:false
        item['dbIsNull']=item['dbIsNull']===1?true:false
        item['isQuery']=item['isQuery']===1?true:false
        item['isShowForm']=item['isShowForm']===1?true:false
        item['isShowList']=item['isShowList']===1?true:false
        item['fieldMustInput']=item['fieldMustInput']==='1'?true:false
        if(item.dbFieldName==='id'||item.dbFieldName==='create_by'||item.dbFieldName==='create_time'||item.dbFieldName==='update_by'||item.dbFieldName==='update_time'){
          item['disabled']=true
        }
        item['select']=false
        return item;
      }
      )
    })
    console.log(this.i)
  }
  checkAll(event){
    console.log(event)
  }
  save(value: any) {
    
    this.http.put(`online/cgform/api/editAll`, {
      deleteFieldIds:[],
      deleteIndexIds:[],
      indexs:this.indexs,
      fields:this.items.map((item: any) => {
        item['dbIsKey']=item['dbIsKey']?1:0
        item['dbIsNull']=item['dbIsNull']?1:0
        item['isQuery']=item['isQuery']?1:0
        item['isShowForm']=item['isShowForm']?1:0
        item['isShowList']=item['isShowList']?1:0
        item['fieldMustInput']=item['fieldMustInput']?1:0
        return item;
      }
      ),
      head:this.i
    }).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }
}
