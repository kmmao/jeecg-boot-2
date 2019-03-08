import { Component, Input, Output, EventEmitter, OnInit, forwardRef, OnChanges, SimpleChanges } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-dict-select',
  template: `
    <nz-select nz_input [(ngModel)]="model" [name]="name" (ngModelChange)="modelChange($event)">
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
  url2 = 'ngAlainController.do?dict&_allow_anonymous=true';
   model: any;
  @Input() name;
  @Input() url;
  @Input() dictCode;
  @Input() dynamicDict = false;
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

  constructor(public http: _HttpClient) { }
  ngOnInit(): void {
    if (this.url == null) {
      this.url = this.url2;
    }
    this.http.get(this.url, { 'code': this.dictCode }).subscribe((res) => (this.dictList = res));
  }
  ngOnChanges(changes: SimpleChanges) {
    if (!changes.dictCode.firstChange && this.dictCode != null && this.dynamicDict) {
      this.model = '';
      this.http.get(this.url, { 'code': this.dictCode }).subscribe((res) => (this.dictList = res));
    }
  }
}
