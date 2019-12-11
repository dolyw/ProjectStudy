# 02-SpringBoot-ES-Docker

> 目录: [https://github.com/dolyw/ProjectStudy](https://github.com/dolyw/ProjectStudy)

#### 软件架构

1. SpringBoot + REST Client

#### 项目介绍

SpringBoot整合ES的方式(TransportClient、Data-ES、Elasticsearch SQL、REST Client)

详细的过程查看: 

* SpringBoot整合Elasticsearch的方式(TransportClient、Data-ES、Elasticsearch SQL、REST Client): [https://github.com/dolyw/ProjectStudy/tree/master/Elasticsearch/01-SpringBoot-ES-Local](https://github.com/dolyw/ProjectStudy/tree/master/Elasticsearch/01-SpringBoot-ES-Local)

* Docker环境下搭建Elasticsearch，Elasticsearch集群，Elasticsearch-Head以及IK分词插件和拼音分词插件:[https://note.dolyw.com/docker/03-Elasticsearch.html](https://note.dolyw.com/docker/03-Elasticsearch.html)

这个项目只是测试Docker版本的Elasticsearch是否安装无误，和之前本地版区别是Docker的ES版本升级到了7.3，字段添加了content，describe，之前的desc是关键字就改成了describe

#### 预览图示

* 查询

![查询](https://docs.dolyw.com/Project/Elasticsearch/image/20190815001.gif)

* 添加

![添加](https://docs.dolyw.com/Project/Elasticsearch/image/20190815002.gif)

* 修改

![修改](https://docs.dolyw.com/Project/Elasticsearch/image/20190815003.gif)

* 删除

![删除](https://docs.dolyw.com/Project/Elasticsearch/image/20190815004.gif)

#### 安装教程

运行项目src\main\java\com\example\Application.java即可，访问[http://localhost:8080](http://localhost:8080)即可

#### 搭建参考

1. 感谢MaxZing的在SpringBoot整合Elasticsearch的Java Rest Client:[https://www.jianshu.com/p/0b4f5e41405e](https://www.jianshu.com/p/0b4f5e41405e)