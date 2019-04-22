import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { DelonABCModule } from '@delon/abc';
import { DelonACLModule } from '@delon/acl';
import { DelonCacheModule } from '@delon/cache';
import { DelonChartModule } from '@delon/chart';
import { DelonFormModule } from '@delon/form';
// delon
import { AlainThemeModule } from '@delon/theme';
// #region third libs
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { CountdownModule } from 'ngx-countdown';
import { NgxTinymceModule } from 'ngx-tinymce';
import { DictPipe } from './pipe/dict.pipe';
import { SafePipe } from './pipe/safe.pipe';
import { DictSelectComponent } from './widgets/dict-select';
import { ImageWrapperComponent } from './widgets/image-wrapper';
import { MutiBarChartComponent } from './widgets/muti-bar-chart';
import { UpLoadComponent } from './widgets/upload';


const THIRDMODULES = [
  NgZorroAntdModule,
  CountdownModule,
  NgxTinymceModule
];
// #endregion

// #region your componets & directives
const COMPONENTS = [
  DictSelectComponent,
  UpLoadComponent,
  ImageWrapperComponent,
  MutiBarChartComponent,
];
const DIRECTIVES = [];
// #endregion
const PIPES = [
  SafePipe,
  DictPipe
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    AlainThemeModule.forChild(),
    DelonABCModule,
    DelonACLModule,
    DelonFormModule,
    DelonChartModule,
    DelonCacheModule,
    // third libs
    ...THIRDMODULES
  ],
  declarations: [
    // your components
    ...COMPONENTS,
    ...DIRECTIVES,
    ...PIPES
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    AlainThemeModule,
    DelonABCModule,
    DelonACLModule,
    DelonFormModule,
    DelonChartModule,
    // third libs
    ...THIRDMODULES,
    // your components
    ...COMPONENTS,
    ...DIRECTIVES,
    ...PIPES,
  ]
})
export class SharedModule { }
