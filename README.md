# 地图平台提供百度四维地图常用接口
## mybatis-plus官方网址：https://mybatis.plus/
## mybatis-plus慕课网教程：https://www.imooc.com/learn/1130
**打jar包：项目名、端口都已配置文件为准**
<br>**打war包：项目名、端口以容器里发布的为准**
## pom文件
**本地启动时**
- pom文件不需要排除内置tomcat

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <!-- <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>-->
</dependency>
```
- application.yml 切换为开发环境dev
```$xslt
spring:
  profiles:
    active: dev
```
- 注释weblogic数据源配置
```$xslt
com/picc/config/DataSourceConfig.java
com/picc/config/DSConfig.java
```
**weblogic启动**
- pom文件排除内置tomcat

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <!-- <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>-->
</dependency>
```
- application.yml 切换为开发环境weblogic
```$xslt
spring:
  profiles:
    active: weblogic
```
- 放开注释weblogic数据源配置
```$xslt
com/picc/config/DataSourceConfig.java
com/picc/config/DSConfig.java
```