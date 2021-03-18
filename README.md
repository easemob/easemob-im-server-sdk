# Easemob IM Java SDK [![build](https://github.com/easemob/easemob-im-server-sdk/workflows/Build/badge.svg)](https://github.com/easemob/easemob-im-server-sdk/actions) [![codecov](https://codecov.io/gh/easemob/easemob-im-server-sdk/branch/master/graph/badge.svg)](https://codecov.io/gh/easemob/easemob-im-server-sdk)

**Easemob IM Java SDK** 是对环信IM [服务端API](http://docs-im.easemob.com/im/server/ready/intro) 的封装。

提供用户、群组等资源的操作管理能力。

```
该项目即将发布，敬请期待。 
```

## 依赖

- Java 1.8
- [Reactor](https://projectreactor.io) 

## 安装

如果你的项目使用Maven构建，可以在pom.xml中添加下面代码：

``` xml
<dependency>
    <groupId>com.easemob.im</groupId>
    <artifactId>im-sdk-core</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

如果你的项目使用Gradle构建，可以在build.grade中添加下面代码：

``` gradle
implementation 'com.easemob.im:im-sdk-core:0.1.0-SNAPSHOT'
```

## 使用

[EMService](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/EMService.html) 是所有API的入口，可以这样初始化：

``` java
EMProperties properties = EMProperties.builder()
        .setAppkey(cliProperties.getAppkey())
        .setClientId(cliProperties.getClientId())
        .setClientSecret(cliProperties.getClientSecret())
        .build();

EMService service = new EMService(properties);
```

根据业务资源，API分为：

- [Attachment](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/attachment/AttachmentApi.html)
  用于上传下载附件
- [Block](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/block/BlockApi.html)
  用于限制访问
- [Contact](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/contact/ContactApi.html)
  用于管理联系人
- [Group](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/group/GroupApi.html)
  用于管理群组
- [Message](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/message/MessageApi.html)
  用于发送消息
- [Notification](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/notification/NotificationApi.html)
  用于管理推送
- [User](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/user/UserApi.html)
  用于管理用户
- [Room](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/room/RoomApi.html)
  用于管理聊天室

每个业务资源对应一个方法，例如，用户相关的API，都可以在.user()找到。

举个例子，我们要注册一个用户，就可以这样写：

``` java
EMService service;
service.user().create(username, password).block();
```

API的返回值是响应式的，如果希望阻塞，可以使用上面例子中的block()。

## DEMO

可以参考[im-sdk-cli](https://github.com/easemob/easemob-im-server-sdk/tree/master/im-sdk-cli) ，这是一个使用该SDK构建的CLI程序。

## 参考

SDK的api文档在[这里](https://easemob.github.io/easemob-im-server-sdk/) 。


