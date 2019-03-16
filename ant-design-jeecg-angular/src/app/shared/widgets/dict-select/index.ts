import { Component, Input, Output, EventEmitter, OnInit, forwardRef, OnChanges, SimpleChanges } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { CacheService } from '@delon/cache';
import { DictService } from '@shared/service/dict.service';

@Component({
  selector: 'app-dict-select',
  template: `
    <nz-select style="width: 100%;" nzPlaceHolder="请选择" nzShowSearch nzAllowClear nz_input [disabled]="disabled" [(ngModel)]="model" [name]="name" [nzMode]="nzMode" (ngModelChange)="modelChange($event)">
        <nz-option *ngFor="let dict of dictList" [nzValue]="dict.value" [nzLabel]="dict.label"></nz-option>
      </nz-select>
    `,
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => DictSelectComponent),
    multi: true
  }],
  styleUrls: ['./index.less']
})
export class DictSelectComponent implements OnInit, ControlValueAccessor, OnChanges {
  model: any;
  @Input() name;
  @Input() url;
  @Input() dictCode: string;
  @Input() dynamicDict = false;
  @Input() nzMode='default';
  @Input() disabled=false;


  @Output() ngModelChange = new EventEmitter();
  dictList;
  public onModelChange: Function = () => { };
  public onModelTouched: Function = () => { };
  writeValue(value: any) {
    this.model = value;
  }
  registerOnChange(fn: Function): void {
    this.onModelChange = fn;
  }
  registerOnTouched(fn: Function): void {
    this.onModelTouched = fn;
  }

  modelChange(value) {
    this.model = value;
    this.onModelChange(this.model); // 主要是要调用这个去重置绑定的model的值
    this.ngModelChange.emit(value);
  }

  constructor(public http: _HttpClient, private dictService: DictService) { }
  ngOnInit(): void {
    this.getDict();
  }
  ngOnChanges(changes: SimpleChanges) {
    if (!changes.dictCode.firstChange && this.dictCode != null && this.dynamicDict) {
      this.model = '';
      this.getDict();
    }
  }
  getDict() {
    const dict = this.dictCode.split(',');
    if (dict.length === 1) {
      this.dictService.getDict(dict[0]).subscribe((res) => (this.dictList = res));
    } else if (dict.length === 3) {
      this.dictService.getDictByTable(dict[0], dict[1], dict[2]).subscribe((res) => (this.dictList = res));
    }
  }
}
