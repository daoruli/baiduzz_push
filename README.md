# baiduzz_push

#### 介绍
这是一个简单的百度站长主动推送的功能，通过解析站点地图文件来实现推送的。可以集成在各位站长的启动项目脚本中
java -jar /root/baiduzz_push.jar 站长域名 百度token 站点地图绝对路径 站点地图中url的节点名称比如loc

#### 软件架构
jdk8 + hutool


#### 安装教程

1.  确保本机有java环境 linux如果没有java环境可以使用 yum install java-1.8.0-openjdk.x86_64 安装 java -version验证
2.  直接将本项目中 baiduzz_push.jar上传到存放项目的服务器上

#### 使用说明

1.  使用  "java -jar baiduzz_push.jar 参数1 参数2 参数3 参数4" 执行 
- `参数1`：您的站长域名 例如 https://www.baidu.com
- `参数2`：您的百度站长token [百度主动推送链接](https://ziyuan.baidu.com/linksubmit/index)
- `参数3`：站点地图的绝对路径例如 /root/hexo_blog/public/sitemap.xml
- `参数4`：站点地图中存放url的节点 例如<loc>https://www.baidu.com</loc> 节点就为loc 代码是根据这个节点去截取的

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
