# talkComunity 
  
## 目录  
* [背景介绍](#背景介绍)  
* [项目介绍](#项目介绍)  
* [使用说明](#使用说明)  
  * [获取代码](#获取代码)  
  * [资料](#资料)  
  * [工具](#开发工具)  
* [其他](#其他) 
* [快速运行](#快速运行)  
  
<a name="背景介绍"></a>  
## 背景介绍  
   
*talkCommunity*，是个网上在线学习交流工具。是在学习SpringBoot的过程中设计并开发。  
  
<a name="项目介绍"></a>  
## 项目介绍  
  
*talkCommuntiy* 的设计初衷是给大家提供一个可方便交流学习的平台，可以用github授权登录，实现搜索、评论、通知、浏览数、评论数、时间备注、热门、相关、分页等基本功能。此项目业务逻辑层用springboot框架实现，日志框架用log4j，Web容器有Tomcat、JBoss ，IDE工具用idea，源码管理工具：Git， 插件：moment、 Lombok，项目构建&依赖管理工具：Maven等工具以及插件链接在下方有说明。此项目分后端接口和前端UI界面两部分。<br>   
  
<a name="使用说明"></a>  
## 使用说明  
  *talkCommuntiy* 目前还没有搭配到服务器，只能通过下载源码运行测试功能。
<a name="获取代码"></a>  
## 获取代码  
github项目源码: <https://github.com/khoing66/talkCommunity/delete/master/eval><br>  
<a name="资料"></a>  
  ## 资料
  [Spring 文档](https://spring.io/guides)    
  [Spring Web](https://spring.io/guides/gs/serving-web-content/)   
  [es](https://elasticsearch.cn/explore)    
  [Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)    
  [Bootstrap](https://v3.bootcss.com/getting-started/)    
  [Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)    
  [Spring](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)    
  [菜鸟教程](https://www.runoob.com/mysql/mysql-insert-query.html)    
  [Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)    
  [Spring Dev Tool](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)  
  [Spring MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)  
  [Markdown 插件](http://editor.md.ipandao.com/)   
  [UFfile SDK](https://github.com/ucloud/ufile-sdk-java)  
  [Count(*) VS Count(1)](https://mp.weixin.qq.com/s/Rwpke4BHu7Fz7KOpE2d3Lw)  
<a name="开发工具"></a> 
## 工具
[Git](https://git-scm.com/download)   
[Visual Paradigm](https://www.visual-paradigm.com)    
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)  
[Lombok](https://www.projectlombok.org)    
[ctotree](https://www.octotree.io/)   
[Table of content sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)    
[One Tab](https://chrome.google.com/webstore/detail/chphlpgkkbolifaimnlloiipkdnihall)    
[Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkig)
[Postman](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
<a name="其他"></a>  
## 其他  
  希望看到该项目对您哪怕一点点的帮助。项目的Bug和改进点以及意见和建议，欢迎随意与我联系沟通。<br>
  联系方式：  
* Email: <khoing@126.com>  
* QQ:925565026  
  
项目的Bug和改进点，可在OSChina上以issue的方式直接提交给我。 


## 快速运行
1. 安装必备工具  
JDK，Maven
2. 克隆代码到本地  
3. 运行命令创建数据库脚本
```sh
mvn flyway:migrate
```
4. 运行打包命令
```sh
mvn package
```
5. 运行项目  
```sh
java -jar target/community-0.0.1-SNAPSHOT.jar
```
6. 访问项目
```
http://localhost:8888
```



