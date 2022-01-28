## 项目启动文档
1. Rabbitmq 
RabbitMQ启动方式有2种
1) 以应用方式启动   
rabbitmq-server -detached 后台启动   
rabbitmqctl stop 关闭   
rabbitmq-server 直接启动，如果你关闭窗口或需要在改窗口使用其他命令时应用就会停止     

2) 以服务方式启动（安装完之后在任务管理器中服务一栏能看到RabbitMQ）   
以管理员方式运行命令提示符   
rabbitmq-service start 开始服务   
rabbitmq-service stop 停止服务   

普通用户进入cmd执行上面的命令可能会报错误   
“发生错误：发生系统错误 5。 拒绝访问。”   
5代表的是：不是系统管理员权限


2. Redis
* 删除所有key   
flushdb 删除当前数据库   
flushall 删除所有数据库
* redis连接情况   
info clients 当前连接数 
config get maxclients 最大连接数
* cluster状态   
cluster nodes
* 查看key在集群中的分片  
cluster keyslot keyName