import { Component, Input, Output, EventEmitter, OnInit, forwardRef, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { UploadFile } from 'ng-zorro-antd';
import { PeConsoleBundleListComponent } from 'app/routes/pe-console/bundle-list/bundle-list.component';

@Component({
  selector: 'app-upload',
  template: `
    <nz-upload [nzAction]="nzAction" [nzListType]="nzListType" [(nzFileList)]="fileList"
               [nzShowButton]="fileList.length < maxLength" [nzPreview]="handlePreview" (nzChange)=handleChange($event) [nzFileType]= "nzFileType" >
      <button nz-button>
        <i nz-icon type="upload"></i><span>点击上传</span>
      </button>
    </nz-upload>
    <nz-modal [nzVisible]="previewVisible" [nzWidth]='nzWidth' [nzContent]="modalContent" [nzFooter]="null" (nzOnCancel)="previewVisible=false">
      <ng-template #modalContent>
        <img [src]="previewImage" [ngStyle]="{ 'width': '100%'}" />
      </ng-template>
    </nz-modal>
  `,
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => UpLoadComponent),
    multi: true
  }],
  styleUrls: ['./index.less']
})
export class UpLoadComponent implements OnInit {
  private model: any ;
  @Input() files;
  fileList = [];
  @Input() nzFileType;
  @Input() nzAction = 'sys/common/upload';
  @Input() nzListType;
  @Input() maxLength = 1;
  @Input() nzWidth = 800;
  previewImage = '';
  type="upload"
  previewVisible = false;

  @Output() ngModelChange = new EventEmitter();
  public onModelChange: Function = () => { };
  public onModelTouched: Function = () => { };

  writeValue(value: any) {
    if(value){
      value.split(",").forEach(item => {
        this.fileList = [ ...this.fileList, {
          uid: -1,
          name: item,
          status: 'done',
          url: item,
          response:{
            message:item
          }
        }]; // 正确使用方式
      })
    }

    this.model = value;
  }
  registerOnChange(fn: Function): void {
    this.onModelChange = fn;
  }
  registerOnTouched(fn: Function): void {
    this.onModelTouched = fn;
  }
  handleChange(event){
    if(event.type==="success"){
      const values :any[]=[]
      event.fileList.forEach(element => {
        values.push(element.response.message)
      });
      console.log(values)
      this.ngModelChange.emit(values.join(","));
      this.model=values.join(",")
      this.onModelChange(this.model); // 主要是要调用这个去重置绑定的model的值
    }
  }
  modelChange(value) {
    this.onModelChange(this.model); // 主要是要调用这个去重置绑定的model的值
    this.ngModelChange.emit(this.fileList[0].response.message);
  }


  handlePreview = (file: UploadFile) => {
    if(this.nzListType!=='picture-card'){
      window.open(this.model)
    }else{
      this.previewImage = file.url || file.thumbUrl;
      this.previewVisible = true;
    }

  }
  constructor(public http: _HttpClient) { }
  ngOnInit(): void {
    console.log(this.nzFileType==='text')

  }
}
