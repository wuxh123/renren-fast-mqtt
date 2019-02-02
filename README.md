前台
https://github.com/wuxh123/renren-fast-vue

代码生成
renren-generator

功能介绍
基于renren-fast，增加了mqtt功能和协议解析功能。
使用org.eclipse.paho.client.mqttv3实现。

1.在测试过程中，发现mqtt掉线重连总是有问题，后来干脆在callback中重新创建连接了。这一步骤和网上大部分教程都不一样，我实测效果比较稳定。

有更好的想法的同学可以改掉。

2.实现了mqtt订阅、发布。订阅收到的消息会放入线程池中处理。

3.协议处理采用责任链模式，由java自动反射创建协议处理bean集合。这样省去了，添加协议相关调用的麻烦。

4.处理返回采用命令模式，每个处理返回cmd类，处理完协议后统一调用cmd.exectu()方法。

5.如果使用idea，直接可以。如果使用sts需要配置一下lombok
