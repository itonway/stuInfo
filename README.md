IT 学习永无止境，我们一直在路上 itontheway   

项目简介：
该项目为学生信息管理系统，主要包含用户管理，角色管理，菜单管理，学生管理，班级管理等。   
后端技术：  
1.springboot  
2.mybatis  
3.easyexcel  
4.mysql  
5.jdk1.8  
前端技术：  
1.layui  
2.jq  
3.ztree  


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
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/登录页.png)

首页：
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/sy.png)

用户管理页面：
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/yh.png)

角色管理页面：
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/js.png)

菜单管理页面：
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/cd.png)

学生管理页面：
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/xs.png)

班级管理页面：
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/bj.png)

课程管理页面： 
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/kc.png)

教师管理页面： 
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/ls.png)

成绩管理页面：  
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/cj.png)


其他资料  
欢迎关注公众号 itontheway，专注于 SpringBoot springCloud redis mongodb MQ jvm docker k8s vue angularjs es6等，根据项目中的实际使用方式，每天更新有价值的实战例子文章。  
![image](https://github.com/itonway/stuInfo/blob/master/info-web/src/main/resources/image/b.png)



