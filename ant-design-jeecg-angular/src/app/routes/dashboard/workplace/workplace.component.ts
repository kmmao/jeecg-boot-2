import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-dashboard-workplace',
  templateUrl: './workplace.component.html',
})
export class DashboardWorkplaceComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  ngOnInit() { }

}
