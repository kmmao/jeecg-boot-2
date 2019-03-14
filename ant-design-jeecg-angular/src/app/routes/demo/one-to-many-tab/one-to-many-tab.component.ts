import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-demo-one-to-many-tab',
  templateUrl: './one-to-many-tab.component.html',
})
export class DemoOneToManyTabComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  ngOnInit() { }

}
