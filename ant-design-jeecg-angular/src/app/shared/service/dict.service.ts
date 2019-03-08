import { Injectable, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { CacheService } from '@delon/cache';

@Injectable({
  providedIn: 'root'
})
export  class DictService {
  dictvalue=[
    {
      "label": "青云店镇",
      "value": "JD01"
    },
    {
      "label": "长子营镇",
      "value": "JD02"
    },
    {
      "label": "采育镇",
      "value": "JD03"
    },
    {
      "label": "礼贤镇",
      "value": "JD04"
    },
    {
      "label": "安定镇",
      "value": "JD05"
    },
    {
      "label": "榆垡镇",
      "value": "JD06"
    },
    {
      "label": "魏善庄镇",
      "value": "JD07"
    },
    {
      "label": "庞各庄镇",
      "value": "JD08"
    },
    {
      "label": "北臧村镇",
      "value": "JD09"
    },
    {
      "label": "兴丰街道",
      "value": "JD10"
    },
    {
      "label": "林校路街道",
      "value": "JD11"
    },
    {
      "label": "观音寺街道",
      "value": "JD12"
    },
    {
      "label": "天宫院街道",
      "value": "JD13"
    },
    {
      "label": "高米店街道",
      "value": "JD14"
    },
    {
      "label": "新媒体",
      "value": "JD15"
    },
    {
      "label": "生物医药基地",
      "value": "JD16"
    },
    {
      "label": "亦庄地区",
      "value": "JD17"
    },
    {
      "label": "黄村地区",
      "value": "JD18"
    },
    {
      "label": "旧宫地区",
      "value": "JD19"
    },
    {
      "label": "西红门地区",
      "value": "JD20"
    },
    {
      "label": "瀛海地区",
      "value": "JD21"
    }
  ];
  constructor(private http:_HttpClient,private cacheService:CacheService) { }
   getDictValue(value:string,dictCode:string):any {
      return this.dictvalue.filter(item =>{
      if (value===item.value){
        return item.label
      }
     })[0].label
   }
   
}
