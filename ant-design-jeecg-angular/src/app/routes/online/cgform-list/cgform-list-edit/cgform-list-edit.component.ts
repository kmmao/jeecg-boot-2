import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema, SFComponent } from '@delon/form';
import { viewTowidget } from '@shared';
import { CacheService } from '@delon/cache';
import { deepCopy } from '@delon/util';

@Component({
  selector: 'app-online-cgform-list-edit',
  templateUrl: './cgform-list-edit.component.html',
})
export class OnlineCgformListEditComponent implements OnInit {
  record: any = {};
  i: any;
  @ViewChild('sf') sf: SFComponent;
  schema: SFSchema = {
    properties: {
    }
  };
  ui: SFUISchema = {
   
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
    private cacheService: CacheService 
  ) {}

  ngOnInit(): void {
    this.i=this.record
    this.cacheService.get(`online/cgform/api/getFormItem/${this.record.columns_code}`).subscribe((res: any) => {
      const properties = (res as any).result.properties;
      for (const key in properties) {
        this.schema.properties[key] = {
          title: properties[key].title,
          maxLength: properties[key].title,
          type: properties[key].type,
        }
        if (properties[key].enum) {
          this.schema.properties[key]['enum'] = properties[key].enum.map((item: any) => {
            return {
              label: item.text,
              value: item.value
            }
          }
          )
        }
        if (properties[key].view) {
          this.schema.properties[key]['ui'] = {
            widget: viewTowidget[properties[key].view]
          }
        }
      }
      try {
         this.sf.refreshSchema();
      } catch (error) {
        console.log('cache is runing')
      }
     
    });
    
  }

  save(value: any) {
    this.http.put(`online/cgform/api/form/${this.record.columns_code}`, value).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }
}
