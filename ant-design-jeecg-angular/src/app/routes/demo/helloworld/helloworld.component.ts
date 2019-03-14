import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-demo-helloworld',
  templateUrl: './helloworld.component.html',
})
export class DemoHelloworldComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  ngOnInit() { }

}
