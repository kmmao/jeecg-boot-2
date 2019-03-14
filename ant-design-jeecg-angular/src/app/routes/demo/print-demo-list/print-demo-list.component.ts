import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-demo-print-demo-list',
  templateUrl: './print-demo-list.component.html',
})
export class DemoPrintDemoListComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  ngOnInit() { }

}
