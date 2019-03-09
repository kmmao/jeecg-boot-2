import { Injectable, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { CacheService } from '@delon/cache';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DictService {

  constructor(private http: _HttpClient, private cacheService: CacheService) { }
  getDictValue(dictcode:string,value:string){
    return ''
  }
  getDict(dictCode: string): Observable<any> {
    return this.cacheService.get(`sys/ng-alain/getDictItems/${dictCode}`)
  }
  getDictByTable(table: string, key: string,value:string): Observable<any> {
    return this.cacheService.get(`sys/ng-alain/getDictItemsByTable/${table}/${key}/${value}`)
  }

}
