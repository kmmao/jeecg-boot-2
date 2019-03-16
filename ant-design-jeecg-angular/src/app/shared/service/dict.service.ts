import { Injectable, OnInit } from '@angular/core';
import { _HttpClient, YNPipe } from '@delon/theme';
import { CacheService } from '@delon/cache';
import { Observable, of } from 'rxjs';




@Injectable({
  providedIn: 'root'
})
export class DictService {

  constructor(private http: _HttpClient, private cacheService: CacheService) { }
  getDictValue(dictcode:string,value:string){
    return ''
  }
  /* 支持在线字典和离线字典 */
  getDict(dictCode: string): Observable<any> {
    if(dict[dictCode]){
      return of(dict[dictCode])
    }else{
      return this.cacheService.get(`sys/ng-alain/getDictItems/${dictCode}`)
    }
  }
  getDictByTable(table: string, key: string,value:string): Observable<any> {
    return this.cacheService.get(`sys/ng-alain/getDictItemsByTable/${table}/${key}/${value}`)
  }

}
/* 离线字典 */
const dict={
  sf:[
    {value:'Y',label:'是'},
    {value:'N',label:'否'}
  ],
  table_type:[
    {value:1,label:'单表'},
    {value:2,label:'主表'},
    {value:3,label:'附表'}
  ],
  form_category:[
    {value:'bdfl_include',label:'导入表单'},
    {value:'bdfl_ptbd',label:'普通表单'},
    {value:'bdfl_fzbd',label:'复杂表单'},
    {value:'bdfl_vipbd',label:'VIP表单'},
  ],
  id_type:[
    {value:'UUID',label:'UUID(36位唯一编码)'},
    {value:'NATIVE',label:'NATIVE(自增长方式)'},
    {value:'SEQUENCE',label:'SEQUENCE(序列方式)'},
  ],
  form_template:[
    {value:'ledefault',label:'ACE默认模板'},
    {value:'default',label:'EASY默认模板'},
  ],
  form_template_mobile:[
    {value:'ledefault',label:'ACE默认模板'},
    {value:'default',label:'EASY默认模板'}
  ],
  query_mode:[
    {value:'single',label:'单表查询'},
    {value:'group',label:'组合查询'}
  ],
  db_type:[
    {value:'string',label:'String'},
    {value:'int',label:'Integer'},
    {value:'double',label:'Double'},
    {value:'Date',label:'Date'},
    {value:'BigDecimal',label:'BigDecimal'},
    {value:'Text',label:'Text'},
    {value:'Blob',label:'Blob'},
  ],
  field_show_type:[
    {value:'text',label:'文本框'},
    {value:'password',label:'密码'},
    {value:'radio',label:'单选框'},
    {value:'checkbox',label:'多选'},
    {value:'date',label:'日期(yyyy-MM-dd)'},
    {value:'datetime',label:'日期（yyyy-MM-dd HH:mm:ss）'},
    {value:'file',label:'文件'},
    {value:'textarea',label:'多行文本'},
    {value:'list',label:'下拉框'},
    {value:'popup',label:'popup弹出框'},
    {value:'image',label:'图片'},
    {value:'umeditor',label:'UE编辑器'},
    {value:'tree',label:'树控件'},
  ],
  field_valid_type:[
    {value:'only',label:'唯一校验'},
    {value:'n6-16',label:'6到16位数字'},
    {value:'6-16',label:'6到16位任意字符'},
    {value:'url',label:'网址'},
    {value:'e',label:'电子邮件'},
    {value:'m',label:'手机号码'},
    {value:'p',label:'邮政编码'},
    {value:'s',label:'字母'},
    {value:'n',label:'数字'},
    {value:'*',label:'非空'},
    {value:'s6-18',label:'6到18位字符串'},
  ],
  index_type:[
    {value:'normal',label:'normal'},
    {value:'unique',label:'unique'},
  ],
}

/* 
            <a-select-option value="only">唯一校验</a-select-option>
            <a-select-option value="n6-16">6到16位数字</a-select-option>
            <a-select-option value="*6-16">6到16位任意字符</a-select-option>
            <a-select-option value="url">网址</a-select-option>
            <a-select-option value="e">电子邮件</a-select-option>
            <a-select-option value="m">手机号码</a-select-option>
            <a-select-option value="p">邮政编码</a-select-option>
            <a-select-option value="s">字母</a-select-option>
            <a-select-option value="n">数字</a-select-option>
            <a-select-option value="*">非空</a-select-option>
            <a-select-option value="s6-18">6到18位字符串</a-select-option>
*/