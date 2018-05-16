# boot-security
## 2018.03.05 spring boot 由1.5.10 升级到2.0.0
## 2018.03.15上传文件的大小配置修改spring.http.multipart改为spring.servlet.multipart
## 2018.04.14 layui升级到2.2.6
## 2018.05.16 修改druid
1. pom中删除druid依赖，改为druid-spring-boot-starter
2. 作废DruidConfig类，druid支持自动注入了，前缀spring.datasource.druid
源码com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper
3. 修改application.yml里的数据源配置，spring.datasource改为spring.datasource.druid


