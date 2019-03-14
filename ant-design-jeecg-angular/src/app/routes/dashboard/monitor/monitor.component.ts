import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-dashboard-monitor',
  templateUrl: './monitor.component.html',
})
export class DashboardMonitorComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  ngOnInit() { }

}
