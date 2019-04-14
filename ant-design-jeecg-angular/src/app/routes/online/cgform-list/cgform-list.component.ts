import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { STColumn, STComponent, STData } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { ModalHelper, _HttpClient } from '@delon/theme';
import { OnlineCgformListAddComponent } from './cgform-list-add/cgform-list-add.component';
import { OnlineCgformListEditComponent } from './cgform-list-edit/cgform-list-edit.component';
import { CacheService } from '@delon/cache';

@Component({
  selector: 'app-online-cgform-list',
  templateUrl: './cgform-list.component.html',
})
export class OnlineCgformListComponent implements OnInit {
  url: string;
  code: string;
  columnsSet;
  searchSchema: SFSchema = {
    properties: {
      no: {
        type: 'string',
        title: '编号'
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    {
      title: '',
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper, private routeInfo: ActivatedRoute,private cacheService: CacheService ) { }

  ngOnInit() {
    this.code = this.routeInfo.snapshot.params['code']
    this.cacheService.get(`online/cgform/api/getColumns/${this.code}`).subscribe(res => {
      this.columnsSet = res;
      (res as any).result.columns.forEach(element => {
        const column: STColumn = {
          title: element.title,
          index: element.dataIndex,
        };
        if (element.customRender) {
          column.format = (item: any) => this.getDictText(element.customRender, item[element.dataIndex])
        }
        this.columns = [...this.columns, column];
      });

      this.columns = [...this.columns,
      {
        title: '',
        buttons: [
          { text: '查看', click: (item: any) => `/form/${item.id}` },
          {
            text: '编辑', type: 'modal',
            modal: {
              component: OnlineCgformListEditComponent,
              params: (record: STData) => {
                record['columns_code'] = this.code
                return record
              }

            },
            click: 'reload'
          },
          {
            text: `删除`,
            type: 'del',
            click: (record, modal, comp) => {
              this.http.delete(`/online/cgform/api/form/484aae9dee13465e916184949e28fe14/${record.id}`).subscribe(res => {
                this.st.reload()
              })
            }
          }
        ]
      }
      ]

      this.url = `online/cgform/api/getData/${this.code}?column=createTime&order=desc`
    })
  }

  add() {
    this.modal
      .static(OnlineCgformListAddComponent, { code: this.code })
      .subscribe(() => this.st.reload());
  }


  getDictText(dictcode, value) {
    let text = ''
    if (!this.columnsSet.result.dictOptions[dictcode]) {
      return value;
    }
    this.columnsSet.result.dictOptions[dictcode].forEach(element => {
      if (element.value === value) {
        text = element.text
      }
    });
    return text;
  }

}
