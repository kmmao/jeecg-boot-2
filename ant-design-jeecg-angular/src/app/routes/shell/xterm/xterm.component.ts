import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { Terminal } from 'xterm';
import { fit } from 'xterm/lib/addons/fit/fit';
const xterm = new Terminal();
fit(xterm);  // Fit the terminal when necessary
@Component({
  selector: 'app-shell-xterm',
  templateUrl: './xterm.component.html',
})
export class ShellXtermComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  ngOnInit() {

  xterm.open(document.getElementById('#terminal'));
  xterm.write('Hello from \x1B[1;3;31mxterm.js\x1B[0m $ ')
   }



}
