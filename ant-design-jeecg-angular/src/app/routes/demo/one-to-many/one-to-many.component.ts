import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-demo-one-to-many',
  templateUrl: './one-to-many.component.html',
})
export class DemoOneToManyComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  ngOnInit() { }

}
