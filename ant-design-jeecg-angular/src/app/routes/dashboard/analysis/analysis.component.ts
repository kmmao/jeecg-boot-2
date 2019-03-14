import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'app-dashboard-analysis',
  templateUrl: './analysis.component.html',
})
export class DashboardAnalysisComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  ngOnInit() { }

}
