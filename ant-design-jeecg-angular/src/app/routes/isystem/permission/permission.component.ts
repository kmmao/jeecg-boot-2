import { Component, OnInit, ViewChild } from '@angular/core';
import { SFSchema, SFUISchema } from '@delon/form';
import { ModalHelper, _HttpClient } from '@delon/theme';
import { NzMessageService, NzTreeComponent } from 'ng-zorro-antd';
import { IsystemPermissionAddComponent } from './permission-add/permission-add.component';

@Component({
  selector: 'app-isystem-permission',
  templateUrl: './permission.component.html',
  styles: [
    `
    [nz-button] {
      margin-right: 8px;
      margin-bottom: 12px;
    }
    nz-input-group {
      padding: 10px 0;
    }
  `
  ]
})
export class IsystemPermissionComponent implements OnInit {
  nodes = [];
  i: any = {};
  @ViewChild('treeCom') treeCom: NzTreeComponent;
  searchValue;
  schema: SFSchema = {
    properties: {
      name: { type: 'string', title: '菜单名称' },
      url: { type: 'string', title: '菜单路径' },
      component: { type: 'string', title: '前端组件' },
      redirect: { type: 'string', title: '默认跳转地址' },
      icon: { type: 'string', title: '菜单图标' },
      sortNo: { type: 'number', title: '排序' },
      hidden: { type: 'boolean', title: '隐藏路由', },
      alwaysShow: { type: 'boolean', title: '聚合路由', },
    },
    required: ['name'],
  };
  ui: SFUISchema = {

  };


  constructor(private http: _HttpClient, private modal: ModalHelper, private message: NzMessageService) { }

  ngOnInit() {
    this.getMenus()
  }


  getMenus() {
    this.http.get('sys/permission/list').subscribe(res => {
      this.nodes = (res as any).result
    });
  }
  nzEvent(event) {
    console.log(event);
    this.i = event.node.origin
  }
  nzSearchValueChange(event) {
    console.log(event)
  }
  add() {
    this.modal
      .createStatic(IsystemPermissionAddComponent, { i: { parentName: `一级菜单` } })
      .subscribe(() => this.getMenus());
  }
  addsub() {
    if (!this.i.key) {
      this.message.error('请选择一个父级菜单')
      return;
    }
    this.modal
      .createStatic(IsystemPermissionAddComponent, { i: { parentId: this.i.key, parentName: this.i.title } })
      .subscribe(() => this.getMenus());
  }
  save(value) {
    this.http.put(`sysdepart/sysDepart/edit`, value).subscribe(res => {
      this.message.info((res as any).message)
      this.getMenus()
    })
  }
}
