import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { EnviromentRoutingModule } from './enviroment-routing.module';
import { EnviromentStandingBookInfoComponent } from './standing-book-info/standing-book-info.component';

const COMPONENTS = [
  EnviromentStandingBookInfoComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    EnviromentRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class EnviromentModule { }
