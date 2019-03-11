Jeecg-Boot 快速开发平台(前后端分离版本 ng-alain版本)
===============

当前最新版本： 1.0（发布日期：20190304）

项目介绍：
-----------------------------------
服务端基于jeecg-boot bug修复版 后续与官方统一版本 前端使用angular ng-alian构建 
目前与vue版本功能一致，完成自定义标签 可以和jeecg 一样的开发体验。


技术架构：
-----------------------------------

#### 后端
- 基础框架：Spring Boot 2.0.3.RELEASE

- 持久层框架：Mybatis-plus_3.0.6

- 安全框架：Apache Shiro 1.4.0-RC2，Jwt_3.4.1

- 数据库连接池：阿里巴巴Druid 1.1.10

- 缓存框架：redis

- 日志打印：logback

- 其他：fastjson，poi，Swagger-ui，quartz, lombok（简化代码）等。


#### 前端
 
- [NG-ALAIN 7.0.1](https://ng-alain.com/)
- [ant-design-angular](https://ng.ant.design/docs/introduce/zh)
- [webpack](https://www.webpackjs.com/),[yarn](https://yarnpkg.com/zh-Hans/)
- [@antv/g2](https://antv.alipay.com/zh-cn/index.html) - Alipay AntV 数据可视化图表
- eslint，[@vue/cli 3.2.1](https://cli.vuejs.org/zh/guide)


#### 开发环境

- 语言：Java 8

- IDE： Eclipse安装lombok插件 或者 IDEA

- 依赖管理：Maven

- 数据库：MySQL5.0  &  Oracle 11g

- 缓存：Redis



#### 技术文档

- 在线演示 ：  [http://47.104.204.117:7080/jeecg-boot/admin/index.html ](http://47.104.204.117:7080/jeecg-boot/admin/index.html)

- 官方文档 ：  [http://jeecg-boot.mydoc.io](http://jeecg-boot.mydoc.io)

- 常见问题 ：  [http://www.jeecg.org/forum.php?mod=viewthread&tid=7816&page=1&extra=#pid21237](http://www.jeecg.org/forum.php?mod=viewthread&tid=7816&page=1&extra=#pid21237)

- QQ交流群 ：  284271917

- 视频教程 ：  待续
 
- 开发工具 ： 推荐服务端 idea ,客户端推荐vscode
 
 

### 功能模块
```
├─系统管理
│  ├─用户管理
│  ├─角色管理
│  ├─菜单管理（权限设置）
│  ├─部门管理
│  └─字典管理
├─智能化功能
│  ├─代码生成器功能（一键生成，包括前端页面也可以生成，绝对是后端开发福音）
├─系统监控
│  ├─定时任务
│  ├─系统日志
│  ├─系统通知
│  ├─SQL监控
│  ├─swagger-ui(在线接口文档)
│─常用示例
│  ├─单表模型例子
│  └─一对多模型例子
│  └─打印例子
│  └─一对多TAB例子
│─更多页面模板
│  ├─各种高级表单
│  ├─各种列表效果
│  └─结果页面
│  └─异常页面
│  └─个人页面
└─其他模块
   └─其他
   
```
   
   

系统效果
----
![输入图片说明](https://images.gitee.com/uploads/images/2019/0311/094112_9182154f_718687.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0311/094142_f0aaf3fc_718687.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0311/094208_5aaad084_718687.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0311/094250_9d215a04_718687.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0311/094314_ca7e580e_718687.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0311/094511_bac767c4_718687.png "屏幕截图.png")

代码效果
1.自定义标签 和原来的Jeecg jsp标签一样好用简单
![输入图片说明](https://images.gitee.com/uploads/images/2019/0311/095851_e693db7b_718687.png "屏幕截图.png")
2.页面与业务代码分离 代码简洁
![输入图片说明](https://images.gitee.com/uploads/images/2019/0311/100030_92c06569_718687.png "屏幕截图.png")
3.表单支持动态生成。

```
import { Component, Input, OnInit } from '@angular/core';
import { SFSchema, SFUISchema } from '@delon/form';
import { _HttpClient } from '@delon/theme';
import { DictService } from '@shared';
import { NzMessageService, NzModalRef } from 'ng-zorro-antd';

@Component({
  selector: 'app-isystem-user-edit',
  templateUrl: './user-edit.component.html',
})
export class IsystemUserEditComponent implements OnInit {
  @Input()
  record: any = {};
  i: any={};
  schema: SFSchema = {
    properties: {
      username: { type: 'string', title: '用户名' },
      realname: { type: 'string', title: '用户姓名' },
      selectedroles: {
        type: 'string', title: '用户角色'
      },
      avatar: { type: 'string', title: '头像' },
      birthday: { type: 'string', title: '生日' },
      sex: {
        type: 'integer',
        title: '性别',
      },
      email: { type: 'string', title: '邮箱' },
      phone: { type: 'string', title: '电话' },
    },
    required: ['username', 'realname', 'selectedroles'],
  };
  ui: SFUISchema = {
    '*': {
      spanLabelFixed: 100,
    },
    $selectedroles: {
      widget: 'select',
      mode: 'tags',
      asyncData: () => this.dictService.getDictByTable('sys_role','role_name','id')
    },
    $avatar: {
      widget: 'upload',
      action:'sys/common/upload',
      listType:'picture-card',
      limit:'1',
      resReName:'message',
    },
    $birthday: {
      widget: 'date',
    },
    $sex: {
      widget: 'select',
      asyncData:()=>this.dictService.getDict('sex')
    },
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
    private dictService:DictService
  ) { }

  ngOnInit(): void {
   this.http.get(`sys/user/queryUserRole?userid=${this.record.id}`).subscribe(res=>{
    this.i = this.record;
    this.i['selectedroles']=(res as any).result
    console.log(this.i)
   })
  }

  save(value: any) {
    value['selectedroles'] = value.selectedroles.join(",");
    this.http.put(`sys/user/edit`, value).subscribe(res => {
      this.modal.close(true);
    });
  }

  close() {
    this.modal.destroy();
  }

}

```

4.列表页简洁生成
![输入图片说明](https://images.gitee.com/uploads/images/2019/0311/100410_0d49f4b7_718687.png "屏幕截图.png")

```
import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { IsystemUserEditComponent } from './user-edit/user-edit.component';
import { NzMessageService } from 'ng-zorro-antd';
import { IsystemUserPasswordUpdateComponent } from './user-password-update/user-password-update.component';
import { IsystemUserViewComponent } from './user-view/user-view.component';
import { IsystemUserAddComponent } from './user-add/user-add.component';

@Component({
  selector: 'app-isystem-user',
  templateUrl: './user.component.html',
})
export class IsystemUserComponent implements OnInit {
  url = `sys/user/list?field=id,,username,realname,avatar,sex,birthday,phone,email,status,createTime,action`;
  searchSchema: SFSchema = {
    properties: {
      username: {
        type: 'string',
        title: '用户账号'
      }
    }
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '用户账号', index: 'username' },
    { title: '真实姓名', index: 'realname' },
    {
      title: '头像',
      type: 'img',
      width: '50px',
      index: 'avatar'
    },
    {
      title: '性别',
      index: 'sex',
      filter: {
        menus: [
          { text: '男', value: '1' },
          { text: '女', value: '2' },
        ]
      },
      format: (item: any) => `${item.sex === 1 ? '男' : "女"}`,
    },
    { title: '生日', type: 'date', dateFormat: 'YYYY-MM-DD', index: 'birthday' },
    {
      title: '创建日期', type: 'date', index: 'createTime', sort: {
        key: 'order',
        reName: {
          ascend: 'asc',
          descend: 'desc'
        }
      }
    },
    { title: '手机号', index: 'phone' },
    {
      title: '操作',
      buttons: [
         {
          text: '编辑', icon: 'edit', type: 'modal',
          modal: {
            component: IsystemUserEditComponent,
          },
          click: (record: any, modal: any) => this.st.reload()
        },
        {
          text: '更多',
          children: [
            {
              text: `查看`,
              type: 'modal',
              modal: {
                component: IsystemUserViewComponent,
              },
            },
            {
              text: `修改密码`,
              type: 'modal',
              modal: {
                component: IsystemUserPasswordUpdateComponent,
              },
            },
            {
              text: `删除`,
              type: 'del',
              click: (record) => this.http.delete(`sys/user/delete?id=${record.id}`).subscribe(res =>this.st.reload())
            },
            {
              text: `冻结`,
              type: 'none',
              pop:true,
              popTitle:'确认冻结吗？',
              click: (record, modal, comp) => {
                this.http.post(`sys/user/frozenBatch`,{'ids':record.id,'status':2}).subscribe(res => {
                  this.st.reload()
                })
              }
            },
          ]
        },
      ]
    }
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper, private message: NzMessageService) { }

  ngOnInit() { }

  add() {
     this.modal
       .createStatic(IsystemUserAddComponent)
       .subscribe(() => this.st.reload());
  }

}

```

后台开发环境和依赖
----
- java
- maven
- jdk8
- mysql
- redis
- 数据库脚步：jeecg-boot\docs\dbsys-init-20190225.sql
- 默认登录账号： admin/123456


前端开发环境和依赖
----
- node
- yarn
- webpack
- eslint
- angular cli
  
如何正确使用淘宝源？#
最简单是使用 networkEnv 插件。

或手动修复：

yarn

yarn config set registry https://registry.npm.taobao.org
yarn config set sass_binary_site http://cdn.npm.taobao.org/dist/node-sass
# 恢复
yarn config delete registry
yarn config delete sass_binary_site
npm

npm config set registry https://registry.npm.taobao.org
npm config set sass_binary_site http://cdn.npm.taobao.org/dist/node-sass
# 恢复
npm config delete registry
npm config delete sass_binary_site



项目下载和运行
----

- 拉取项目代码
```bash
git clone https://gitee.com/dangzhenghui/jeecg-boot
cd  jeecg-boot/ant-design-jeecg-angular
```

1. 安装node.js
2. 切换到ant-design-jeecg-angular文件夹下
```
# 安装yarn
npm install -g yarn
npm install -g @angular/cli

# 下载依赖
yarn install
或
npm install

# 启动
npm start
或
yarn start

# 编译项目
yarn run build

# Lints and fixes files
yarn run lint
```

