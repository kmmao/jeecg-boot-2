import { PipeTransform, Pipe } from '@angular/core';
import { CacheService } from '@delon/cache';
import { map, filter, scan } from 'rxjs/operators';




@Pipe({
    name: 'dict'
})
export class DictPipe implements PipeTransform {
    constructor(private cacheService: CacheService) { }
    value: any;
    transform(value: string, args?: any): any {
      return   this.cacheService.get(`ngAlainController.do?dict&code=${args}`).pipe(map(item =>{
            console.log(item);
           return item[value]
        } ));
    }
}
