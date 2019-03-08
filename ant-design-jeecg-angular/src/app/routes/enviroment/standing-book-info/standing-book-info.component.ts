import { Component, OnInit, ViewChild } from '@angular/core';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { ModalHelper, _HttpClient } from '@delon/theme';
import { DictService } from '@shared';

@Component({
  selector: 'app-enviroment-standing-book-info',
  templateUrl: './standing-book-info.component.html',
})
export class EnviromentStandingBookInfoComponent implements OnInit {
  url = `standingBookInfoController.do?datagrid&field=id,questionId,questionFlag,bpmStatus,cityName,streetName,questionAddr,questionClassify,questionRemark,questionSource,glBlame,zyBlame,zfBlame,jdBlame,createTime,completTiem,original,goBackQuestionFlag,appOp,`;
  searchSchema: SFSchema = {
    properties: {
      id: {
        type: 'string',
        title: '编号'
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '主键', index: 'id' },
    { title: '问题编码', index: 'questionId' },
    { title: '流程状态', index: 'bpmStatus' },
    { title: '辖区名称', index: 'cityName' },
    {
      title: '街道镇名称',
      index: 'streetName',
      format:(value:any) =>this.dictSerivce.getDictValue(value.streetName,'streetcode')
    },
    {
      title: '',
      buttons: [
        // { text: '查看', click: (item: any) => `/form/${item.id}` },
        // { text: '编辑', type: 'static', component: FormEditComponent, click: 'reload' },
      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper,private dictSerivce:DictService) { }

  ngOnInit() { }

  add() {
    // this.modal
    //   .createStatic(FormEditComponent, { i: { id: 0 } })
    //   .subscribe(() => this.st.reload());
  }

}
