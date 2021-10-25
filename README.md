# Alive
An AI integrated interactive academic learning and course preparing platform

## 功能概述
Alive智能课堂致力于解决在线教育教师备课难，数字素材准备费时费力的问题。并为学生的在线学习提供一种主动互动式的体验。我们的目标是最终通过人工智能技术，将原始的教学文本素材和原生的音视频资料自动地转化成能提供交互式课堂体验的智能课件，降低教师备课成本，提升学生学习质量。

目前已实现通过半自动化的方式，灵活组合数字素材的来生互动式成课件。
支持插入音频、视频、音视频录制、语音合成、选择题、问答题、填空题、拍照、表情、幻定、打卡等数字资源和互动方式。

## 功能架构
![1635147922(1)](https://user-images.githubusercontent.com/93106185/138655165-4f22e1e7-053d-4aa6-8478-15384912ddb9.png)

## 目录结构
对核心业务模块的目录结构做说明
```
/shaonianpai
├── src
    ├── main
        ├── java  
            ├── com.wechat
                ├── controller   业务接口控制层(重写)       
                ├── jfinal    业务接口控制层(在使用)
                    ├── api   
                        ├── lesson  课程操作接口控制层 
                        ├── student  学生操作接口控制层
                        ├── teacher  教师操作接口控制层
                    ├── service   业务逻辑服务层
                    ├── model   业务对象
                    ├── Oauth2   开放授权服务
                    ├── renderPage  重定向服务
                    ├── common   共享服务
                ├── qiniu    对象存储服务
                ├── service    后台业务处理服务(重写) 
        ├── resources     项目配置文件
        ├── webapp   
        
```

## 页面预览

### 备课端

![image](https://user-images.githubusercontent.com/93106185/138655229-864b0076-2ae3-472e-a5ba-b5cc8386dce1.png)
![image](https://user-images.githubusercontent.com/93106185/138655263-e44b8c96-e73a-4140-8961-6fbe13411815.png)
![image](https://user-images.githubusercontent.com/93106185/138655299-14e4fa88-c065-4e75-942d-a544cc190976.png)

### 上课端
![1635148530(1)](https://user-images.githubusercontent.com/93106185/138656668-c9e43a71-9185-4f4f-a0c2-10d38d9f83e3.png)

![1635148544(1)](https://user-images.githubusercontent.com/93106185/138656676-a77ed2e2-f2e2-43c0-9dcd-4e42160e9455.png)

## 部署

### 部署前准备：
1. com.wechat.util.Keys  有一些第三方服务的连接信息，需要填写
2. resources/redis.properties  需要配置redis连接信息
3. resources/config.properties 需要配置url
4. resources/config_formal/jdbc.properties 需要配置数据库连接信息
5. resources/config_dev/jdbc.properties 需要配置数据库连接信息
### 服务支持：
1. mysql 版本：5.*
2. tomcat 版本：7.*
3. jdk版本：1.8
### 部署：
1. 打包成 .war
2. 将 .war文件复制到 tomcat 的webapps 目录下即可。

