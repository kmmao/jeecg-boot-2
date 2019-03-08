import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Buffer } from 'buffer';

@Component({
  selector: 'app-sys-link',
  templateUrl: './link.component.html',
})
export class SysLinkComponent implements OnInit {
  public url: string;

  constructor(private http: _HttpClient, private modal: ModalHelper, private sanitizer: DomSanitizer, private routeInfo: ActivatedRoute) { }

  ngOnInit() {
    this.url = new Buffer(this.routeInfo.snapshot.params['url'], 'base64').toString();
    this.url = unescape(this.url);
    console.log(this.url);
  }
}
