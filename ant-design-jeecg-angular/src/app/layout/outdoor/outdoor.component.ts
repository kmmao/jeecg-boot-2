import { Component } from '@angular/core';

@Component({
  selector: 'layout-outdoor',
  templateUrl: './outdoor.component.html',
  styleUrls: ['./outdoor.component.less'],
})
export class LayoutOutdoorComponent {
  links = [
    {
      title: '帮助',
      href: '',
    },
    {
      title: '隐私',
      href: '',
    },
    {
      title: '条款',
      href: '',
    },
  ];
}
