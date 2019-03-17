import { Injectable, OnInit } from '@angular/core';
import { _HttpClient, YNPipe } from '@delon/theme';
import { CacheService } from '@delon/cache';
import { Observable, of } from 'rxjs';
import { DICT } from '@shared/const/dict.const';




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
    if(DICT[dictCode]){
      return of(DICT[dictCode])
    }else{
      return this.cacheService.get(`sys/ng-alain/getDictItems/${dictCode}`)
    }
  }
  getDictByTable(table: string, key: string,value:string): Observable<any> {
    return this.cacheService.get(`sys/ng-alain/getDictItemsByTable/${table}/${key}/${value}`)
  }
}