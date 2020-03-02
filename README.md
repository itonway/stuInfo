# info\\
项目简介：
该项目使用springboot + layui，导出使用阿里的EasyExcel 。

jar包启动项目 
mvn install 
java -jar info-web-0.1.1.jar


运行项目步骤：
1.执行info-dao\src\main\resources\database  test.sql
2.启动项目 InfoWebApplication 
3.访问路径 http://localhost:8081/login/jumpLogin


项目结构说明
maven 多模块项目
info-common 放一些通用的东西
info-dao dao层，mapper文件
info-entity 实体类，form
info-service 接口和实现
info-web controller,拦截器，过滤器 

tpl 放在 info-web\src\main\resources\templates
js 放在  info-web\src\main\resources\static\js 

登录页：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/登录页.png)

首页：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/sy.png)

用户管理页面：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/yh.png)

角色管理页面：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/js.png)

菜单管理页面：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/cd.png)

学生管理页面：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/xs.png)

班级管理页面：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/bj.png)

课程管理页面：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/kc.png)

教师管理页面：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/ls.png)

成绩管理页面：
![image](https://github.com/learnItOnTheWay/stuInfo/tree/master/info-web/src/main/resources/image/cj.png)





