# Easemob IM Java SDK [![build](https://github.com/easemob/easemob-im-server-sdk/workflows/Build/badge.svg)](https://github.com/easemob/easemob-im-server-sdk/actions) [![codecov](https://codecov.io/gh/easemob/easemob-im-server-sdk/branch/master/graph/badge.svg)](https://codecov.io/gh/easemob/easemob-im-server-sdk)

**Easemob IM Java SDK** 是对环信IM [服务端API](http://docs-im.easemob.com/im/server/ready/intro) 的封装。

提供用户、群组等资源的操作管理能力。

## 依赖

- Java 1.8
- [Reactor](https://projectreactor.io)(io.projectreactor:reactor-bom:2020.0.4)

## 安装

如果你的项目使用Maven构建，可以在pom.xml中添加下面代码：

``` xml
<dependency>
    <groupId>com.easemob.im</groupId>
    <artifactId>im-sdk-core</artifactId>
    <version>0.2.5</version>
</dependency>
```

如果你的项目使用Gradle构建，可以在build.grade中添加下面代码：

``` gradle
implementation 'com.easemob.im:im-sdk-core:0.2.5'
```

## 准备
使用Server SDK的前提需要您准备appkey相关信息:

如果您有环信管理后台账号并创建过应用，请进入 [这里](https://console.easemob.com/user/login) 进行登录。
![图片](https://user-images.githubusercontent.com/15087647/114996679-a34cb980-9ed1-11eb-89ae-a22c1af7d69d.png)

如图点击查看后，可以看到自己的appkey、Client ID、ClientSecret，用于SDK的初始化。



如果您没有环信管理后台账号，请进入 [这里](https://console.easemob.com/user/register) 进行注册账号，注册成功后请进行登录。
![图片](https://user-images.githubusercontent.com/15087647/114997381-59180800-9ed2-11eb-968a-a29406c78021.png)

如图先添加应用(也就是创建appkey，自动生成Client ID、ClientSecret)，添加成功后在应用列表中可以看到应用信息，点击查看可以看到自己的appkey、Client ID、ClientSecret，用于SDK的初始化。


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


