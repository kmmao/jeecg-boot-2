import { Component, Input, Output, EventEmitter, OnInit, forwardRef, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { UploadFile } from 'ng-zorro-antd';

@Component({
  selector: 'app-upload',
  template: `
  <nz-upload [nzAction]="nzAction" [nzListType]="nzListType" [(nzFileList)]="fileList"
  [nzShowButton]="fileList.length < maxLength" [nzPreview]="handlePreview" [nzFileType]= "nzFileType" >
  <i nz-icon type="plus"></i>
  <div class="ant-upload-text">点击上传</div>
</nz-upload>
<nz-modal [nzVisible]="previewVisible" [nzWidth]='nzWidth' [nzContent]="modalContent" [nzFooter]="null" (nzOnCancel)="previewVisible=false">
  <ng-template #modalContent>
    <img [src]="previewImage" [ngStyle]="{ 'width': '100%'}" />
  </ng-template>
</nz-modal>
    `,
  styleUrls: ['./index.less']
})
export class UpLoadComponent implements OnInit {
  private model: any = [];
  @Input() files;
  fileList = [];
  @Input() nzFileType;
  @Input() nzAction = 'sys/common/upload';
  @Input() nzListType;
  @Input() maxLength = 1;
  @Input() nzWidth = 800;
  previewImage = '';
  previewVisible = false;
  handlePreview = (file: UploadFile) => {
    this.previewImage = file.url || file.thumbUrl;
    this.previewVisible = true;
  }
  constructor(public http: _HttpClient) { }
  ngOnInit(): void {
    console.log(this.files)
    if(this.files){
      this.files.split(",").forEach(item => {
        const value={
          uid: -1,
          name: item,
          status: 'done',
          url: item,
          response:{
            message:item
          }
        }
      console.log(item)
        this.fileList = [ ...this.fileList, value]; // 正确使用方式
      })
    }
   



    
  }
}
