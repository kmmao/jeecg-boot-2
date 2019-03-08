import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { NzModalRef, NzMessageService, NzFormatEmitEvent, NzTreeComponent, NzTreeNode, NzDrawerRef } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema } from '@delon/form';

@Component({
  selector: 'app-isystem-role-appr',
  templateUrl: './role-appr.component.html',
})
export class IsystemRoleApprComponent implements OnInit {
  @Input()
  record: any;
  @ViewChild('nz_tree') nz_tree: NzTreeComponent;
  data = []
  ids:string;
  defaultSelectedKeys = []
  parentIds = {};
  checkedKeys: NzTreeNode[];
  constructor(
    private ref: NzDrawerRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
  ) { }

  ngOnInit(): void {
    this.http.get('sys/permission/queryTreeList').subscribe(res => {
      this.data = (res as any).result.treeList
      this.data.forEach(item => {
        if (!item.isLeaf) {
          this.parentIds[item.key] = true
        }
      })

      this.http.get(`sys/permission/queryRolePermission?roleId=${this.record.id}`).subscribe(res1 => {
        this.defaultSelectedKeys = (res1 as any).result.filter(item => !this.parentIds[item])
      });

    });
  }

  ok() {
      this.ids='';
      this.getIds(this.nz_tree.getTreeNodes());
    this.http.post("sys/permission/saveRolePermission", { 'permissionIds': this.ids, 'roleId': this.record.id }).subscribe(res => {
      this.ref.close((res as any).message);
      this.cancel();
    })

  }

  getIds(items: any[]){
    items.forEach(item => {
      if (item.isLeaf) {
        if (item.isChecked) {
          this.ids = this.ids + ',' + item.key;
        }
      }else{
        if(item.isHalfChecked||item.isChecked){
          this.ids =this.ids + ',' + item.key;
          this.getIds(item.children)
        }
      }
    })
  }

  cancel() {
    this.ref.close();
  }
  nzEvent(event: NzFormatEmitEvent): void {

  }
}
