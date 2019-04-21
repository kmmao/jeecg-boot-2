import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { ShellRoutingModule } from './shell-routing.module';
import { ShellXtermComponent } from './xterm/xterm.component';

const COMPONENTS = [
  ShellXtermComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    ShellRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class ShellModule { }
