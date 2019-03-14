import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-demo-online',
  templateUrl: './online.component.html',
})
export class DemoOnlineComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  ngOnInit() { }

}
