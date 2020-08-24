
# GIL-mms

#### 项目演示地址：[mms.zebin.shop](https://mms.zebin.shop)（所有数据均做脱敏处理）

> 本repository是GIL-mms后端工作
> 可直接导入该项目于本地，修改配置文件中的数据库连接信息，导入附带数据库结构的SQL文件可直接生成所有表，项目中使用到的关于阿里云功能还需开发者自行前往阿里云进行相应功能开通。

#### 项目介绍  
- GIL-mms是一个北京大学GIL实验室信息系统，基于Vuejs+SpringBoot+MyBatis-Plus实现，系统包含首页虚拟三维地球影像服务、每日打卡，日报在线编辑提交、学生管理、教师管理、组别管理、账户管理等模块。其中三维地球影像服务结合我们在实验室的三维重建工作，将北大勺园三维模型在三维地球中集成并展示。

## 页面展示

##### 登录页面
![](https://zebin.shop/img/20200821221844.png)


##### 首页展示

![](https://zebin.shop/img/image-20200821222032063.png)

##### 三维影像展示

![](https://zebin.shop/img/20200823203813.jpeg)

##### 编辑日报

![](./img/homepage.gif)

##### 上传图片

![](./img/upload_img.gif)


##### 日报汇总

![](./img/show_diary.gif)


##### 每日打卡
![](https://zebin.shop/img/20200822092612.png)



##### 学生管理

![](https://zebin.shop/img/20200822093312.png)

##### 

## 项目需求
#### 项目背景
学院之前的信息系统使用JSP开发，目前使用体验已经严重落后，在此基础上， 设计并实现一套前后端分离架构的信息系统，可以更好的支持学院和实验室的日常工作。通过新旧系统并行开发、重构项目的过程来发现其中的潜在难题，并且可以在编码的过程中总结和发现问题、解决问题，从而提高团队协作、解决问题的能力。

#### 功能需求
###### 主页
- 三维地球影像服务，对接后台OSS服务器实现加载实验室i23d系统重建的大规模三维场景。
- 基于MapServer+PostgreSQL+PostGIS部署地理信息服务，存储格栅数据与矢量数据存储影像数据与地理信息，可实现cesium三维地球的离线影像及瓦片影像地图加载、地理位置搜索等功能。

###### 打卡模块
- 每日打卡功能，可选择当日心情和备注文本
- 分页、页码跳转、条件查询功能，支持姓名模糊搜索、时间区间搜索
- 打卡统计功能，可统计学生每周打卡次数

###### 日报模块

- 日报在线编辑功能，使用markdown编辑器，支持实时渲染预览，插入代码等
  - 后端接入OSS服务器，支持上传图片保存到云端图床等功能
- 日报搜索与查看功能
  - 支持姓名模糊搜索、时间区间搜索
  - 支持分页查询，页码跳转功能
  - mavonEditor实时渲染md语法文本

###### 学生模块

- 新增、编辑、删除学生功能，填写必填与选填信息
- 分页、页码跳转、条件查询功能，支持以学号、姓名、学历、入学年份、是否毕业等条件进行查询
- 列表排序功能，支持以本周打卡次数为条件进行升序或降序的查询

###### 教师管理

- 新增、编辑、删除教师功能，填写必填与选填信息
- 分页、页码跳转、条件查询功能，支持以学号、姓名、学历、入学年份、是否毕业等条件进行查询
- 查询、新增、编辑中，所在组选项可对组别数据的实时查询与选择

###### 组别管理

- 新增、编辑、删除账户功能，填写必填与选填信息
- 分页、页码跳转、条件查询功能，支持以组名、负责人、联系方式等条件进行查询

###### 账户管理

- 新增、编辑、删除账户功能，填写必填与选填信息
  - 新增账户根据角色的选择可实时在学生列表或教师列表增添人员信息
- 分页、页码跳转、条件查询功能，支持以账号、姓名等条件进行查询

###### 角色与权限

- 设置教师与学生两种角色、管理员与普通用户两种权限
- 角色选项可查看不同层级的信息，但无法决定是否CRUD学生或教师记录
  - 教师角色可查看教师管理及学生管理模块，无打卡和日报功能
  - 学生角色无法查看教师管理及学生管理模块，有打卡和日报功能
- 权限选项决定是否有权增删改各个模块的信息
	- 管理员权限可查看及管理各个模块的信息
	- 用户权限不可管理信息

###### 个人信息及密码

- 右上角下拉列表中可修改个人信息及密码
- 密码校验、密码确认功能
- 根据角色的不同修改当前角色的信息

#### Build Setup
##### 前端
```sh
# install dependencies
npm install

# serve with hot reload at localhost:8888
npm run server

# build for production with minification
npm run build
```
##### 后端

- 可以使用docker方式部署，也可支持-jar方式
- 使用SpringBoot自带方式打包

#### 非功能需求
##### 性能需求
- 首页加载时间不超过10秒钟
- 列表查询时间不超过2秒钟
- 文章页响应时间不超过2秒钟

## 项目设计

#### 总体设计
- **用到的技术和框架**
- **后端技术**

| 工具 | 名称 |
| ------------ | ------------|
| 后端框架 | SSM |
| ORM  | Mybatis-Plus |
| 数据层代码生成  | AutoGenerator |
| 分页插件  | Mybatis-Plus-pagination |
| 数据库  | MySQL 8.0 |
| 简化对象封装工具 | Lombok|
| 安全框架  | SpringSecurity |
| 登录令牌  | JWT |
| 对象存储  | OSS |
| 项目构建  | Maven |

- **前端技术**
|  工具 | 名称 |
| ------------ | ------------|
| 前端框架 | Vue |
| 路由框架	 | Vue-router |
| 全局状态管理框架	 | Vuex |
| 前端HTTP框架	 | Axios |
| UI框架 | ElementUI |
| MarkDown编辑器插件 | mavonEditor |
| 三维地球影像 | Cesium |
#### 后端结构设计

![](https://zebin.shop/img/20200823015607.png)


## 业务设计
#### 增删改信息流程

![](https://zebin.shop/img/pipeline4.png)

#### 登录流程

![](https://zebin.shop/img/pipeline2.png)

#### 编辑并提交日报流程

![](https://zebin.shop/img/pipeline3.png)

## 打包、部署和运行
- 采用Springboot的maven插件进行打包，打包结果：****.jar
- 部署方式：使用 nohup java -jar ******.jar >******.log 2>&1 &的方式，后台启动项目，并在该路径下生成运行日志

## 数据设计

###### 账户表：tb_account

| 名称  | 类型  |  长度 |  主键 | 非空  | 描述 |
| ---- | ---- | ---- | ---- | ---- | ---- |
| id  | int  |  11 |  true | True | 主键，自增 |
| username  | varchar  | 255  |  false | True |  用户名|
| password  |  varchar |  255 |  false | True | 密码 |
| name  | varchar  | 255  |  false | True | 姓名 |
| email  | varchar  | 255  | false  | True | 邮箱 |
| identity  | varchar  | 255  | false  | True | 身份类别 |
| permission  | varchar  | 255  | false  | True | 权限类别 |
| identity_num  | varchar  | 255  | false  | True | 身份码 |

###### 学生表：tb_student
| 名称  | 类型  |  长度 |  主键 | 非空  | 描述 |
| ---- | ---- | ---- | ---- | ---- | --- |
| id  | int  |  11 |  true |  true | 主键，自增 |
| stu_num | varchar | 100 | false | true  | 学号 |
| name | varchar  | 100 |  false | true  | 姓名 |
| email |  varchar | 255 |  false | true  | 邮箱 |
| clockin_num | int |  | false  | false | 本月打卡数 |
| birthday  | date  |   |  false | false | 生日 |
| entry_year |  varchar | 100 |  false | false | 入学年份 |
| college | varchar  | 100 | false  | false | 院系 |
| is_graduate  |  int |   | false  | false | 院系 |
| level |  int |   | false  | false | 学历 |
| mobile  | varchar  | 100 |  false | false | 手机号码 |
| uid  | int  |   |  false | false | 账户id |
| address  | varchar | 255 |  false | false | 住址 |

###### 教师表：tb_teacher
| 名称  | 类型  |  长度 |  主键 | 非空  | 描述 |
| ---- | ---- | ---- | ---- | ---- | --- |
| id  | int  |  11 |  true |  true | 主键，自增 |
| tch_num | varchar | 100 | false | true  | 教工号 |
| name | varchar  | 100 |  false | true  | 姓名 |
| email |  varchar | 255 |  false | true  | 邮箱 |
| level |  int |   | false  | false | 职级 |
| mobile  | varchar  | 100 |  false | false | 手机号码 |
| uid  | int  |   |  false | false | 账户id |
| office  | varchar | 255 |  false | false | 办公室 |
| team_id  | int | 0 |  false | false | 所在组组号 |

###### 组别表：tb_team
| 名称  | 类型  |  长度 |  主键 | 非空  | 描述 |
| ---- | ---- | ---- | ---- | ---- | --- |
| id  | int  |  11 |  true |  true | 主键，自增 |
| name | varchar  | 100 |  false | true  | 组名 |
| linkman  | varchar  | 100 |  false | true | 联系人 |
| mobile  | varchar  | 100 |  false | true | 手机号码 |
| remark  | varchar  | 255 |  false | false | 备注 |

###### 打卡表：tb_clockin
| 名称  | 类型  |  长度 |  主键 | 非空  | 描述 |
| ---- | ---- | ---- | ---- | ---- | --- |
| id  | int  |  11 |  true |  true | 主键，自增 |
| date  | date  |  |  false | true | 日期 |
| time  | time  |  |  false | true | 时间 |
| emotion_type  | int  |  |  false | true | 打卡心情类别 |
| remark  | varchar  | 255 |  false | false | 备注 |
| uid  | int  |  |  false | true | 账户id |

###### 打卡表：tb_diary
| 名称  | 类型  |  长度 |  主键 | 非空  | 描述 |
| ---- | ---- | ---- | ---- | ---- | --- |
| id  | int  |  11 |  true |  true | 主键，自增 |
| date  | date  |  |  false | true | 日期 |
| time  | time  |  |  false | true | 时间 |
| title  | varchar  | 255 |  false | true | 标题 |
| url  | varchar  | 255 |  false | true | 日报url |
| uid  | int  |  |  false | true | 账户id |

## 开发流程
###### 数据库CRUD
- controller层中编写前端接口，接收前端参数
- service层中编写所需业务接口，供controller层调用
- 实现service层中的接口，并注入mapper层中的sql接口
- 分页及连表查询中，采用xml方式手写SQL语句
- 关于事务，在启动类中开启事务，并在service层需要实现事务的业务接口上使用`@Transactional`注解，在需要回滚处抛出定义的自定义异常

###### 前端部分
- 作为一名（自诩为）后端开发的同学来说，前端一开始真的一脸懵，但时间紧任务重，请做前端的同学喝了杯咖啡，期间认真取了取经，就开始了（瞎子走夜路）的过程。。。
- 安装npm，nodejs，安装vue，vue-cli，element-ui，学习ajax异步请求、路由转发、跨域、学习组件化开发的过程，7天时间从对前端一窍不通到做出了一个简陋的前端工程，鬼知道我熬了多少夜查了多少Google。。。
- 期间遇到的前后端对接的坑就多到不想说了。。。

###### 其他功能
- 使用lazyload插件实现图片懒加载
- cesium的实现又是另一个前后端故事了，需要配置一台地理信息系统的mapserver服务器，配置PostgreSQL与postGIS存放栅格影像与矢量数据等。当然，可以使用在线地图地形，不过作为为学院和实验室开发的项目，用自己部署的更稳妥。

###### 上线发布
- 项目完成之后没有立即对新旧系统进行切换，因为不知道哪个地方处理的不一致就会导致无法想象的后果，因此进行灰度发布，新旧系统同时运行，旧系统中进行的每一次操作，都会在新系统中进行相同的流程，以此来判断两个系统业务逻辑上是否存在差异，从而更安全的上线发布。
- 因为部署在学院和实验室的系统只能是内网访问，因此没有进行网站备案，只在自己的个人主页进行了备案。

## 总结
#### 开发中遇到的难点
- 遇到的第一个难题就是前后端如何传递信息，肯定是使用RESTFul接口，但最重要的是，要设置一套通用的接口，让前后端对接变得更加统一，在查阅了一些文档和代码后，决定自己定义消息传递类，一个请求类，一个响应类，请求类为xxxREQ，字段为各个方法，应对每个模块的条件查询，响应类为Result，同时设置了响应枚举，分别设置成功代码2000、失败代码900，封装好static方法，在Controller中返回对应的Result并传入对应的数据即可。
- 在开发新增账户同时新增学生记录或教师记录的过程中遇到了事务回滚的问题，本以为直接在对应方法上标注`@Transactional`即可，但万万没想到这样做是不对的，当新增一条账户后会拿着学号或教工号在学生表或教师表中插入，学号和教工号设置的是唯一索引，所以一旦遇到重复，那就肯定会返回false，但发现账户依旧添加在了账户表中，后来通过查阅资料知道，如果想让事务回滚，一定要返回异常或添加`TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); `，所以我在判断学生表未插入的位置设置了抛出异常，为此专门写了一个异常类来抛出异常，结果万万没想到，还是没用，我又查阅资料发现是自己马虎了，应该是抛出`RuntimException`才可以，自定义异常直接继承自`Exception`，当然不可以，后来通过查资料又发现，在`@Transactional`中设置`rollbackFor = Exception.class`就可以抛出任何异常后进行事务回滚。
- 在开发根据日期区间查找记录的功能中，遇到了前后端传递集合列表的情况，前端设置为`''`，`null`时，后端都无法正确解析，将前端设置为`[]`完美解决，后来总结了一下，发现这种问题的出现是自己对Spring自动封装对象原理的不了解所导致，因此接下来会好好学习一下Spring的自动封装原理。
- 项目中最大的难点还是莫过于前端工程的开发，但幸好Vue.js的中文文档写的异常的好，非常方便就能查找所需api与功能，所以在此特地感谢尤雨溪大大。

#### 信息系统优缺点
- 首先最大的一个缺点就是在前端页面开发过程中由于前端水平实在有限所以有些代码可能写的非常冗余，在之后学习的过程中会对这部分进行重构和优化
- 由于首页地球影像服务中加载的北大三维模型非常大，所以很吃内存，以及地图影像服务的带宽太小，导致首页加载时很卡，严重影响用户体验，貌似有更加轻量化的模型类型，之后会考虑提高优化这部分的体验。
- 后端部分明确的分工有利于项目的理解与维护

#### 项目后期优化
- 目前项目首页以及地球响应时间过长，后期最好优化到3s响应时间
- 后期数据量及用户数增多后，使用Redis缓存优化高频数据的加载

## 许可证

[Apache License 2.0](https://github.com/CHRIS-WiNG/GIL-mms/blob/master/LICENSE)

Copyright (c) 2020 WangZebin