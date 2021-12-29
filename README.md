# Easemob IM Java SDK [![build](https://github.com/easemob/easemob-im-server-sdk/workflows/Build/badge.svg)](https://github.com/easemob/easemob-im-server-sdk/actions) [![codecov](https://codecov.io/gh/easemob/easemob-im-server-sdk/branch/master/graph/badge.svg)](https://codecov.io/gh/easemob/easemob-im-server-sdk)

**Easemob IM Java SDK** 是对环信IM [服务端API](http://docs-im.easemob.com/im/server/ready/intro) 的封装。

提供用户、群组等资源的操作管理能力。

## 依赖

- Java 1.8
- [Reactor](https://projectreactor.io)(io.projectreactor:reactor-bom:2020.0.4)

## 安装

如果你的项目使用Maven构建，在pom.xml中添加下面代码即可：

``` xml
<dependency>
    <groupId>com.easemob.im</groupId>
    <artifactId>im-sdk-core</artifactId>
    <version>0.4.6</version>
</dependency>
```

如果你的项目使用Gradle构建，可以在build.grade中添加下面代码：

``` gradle
implementation 'com.easemob.im:im-sdk-core:0.4.6'
```

## 准备
使用 Server SDK 的前提需要您准备:
* 1. 环信 appKey
* 2. 两种 appCredentials 中的其中之一:
    - 2.1 环信 clientId, clientSecret
    - 2.2 声网 appId, appCert

### 获取环信 appKey, clientId, clientSecret

如果您有环信管理后台账号并创建过应用，请进入 [这里](https://console.easemob.com/user/login) 进行登录。
![图片](https://user-images.githubusercontent.com/15087647/114996679-a34cb980-9ed1-11eb-89ae-a22c1af7d69d.png)

如图点击查看后，可以看到自己的appkey、Client ID、ClientSecret，用于SDK的初始化。

如果您没有环信管理后台账号，请进入 [这里](https://console.easemob.com/user/register) 进行注册账号，注册成功后请进行登录。
![图片](https://user-images.githubusercontent.com/15087647/114997381-59180800-9ed2-11eb-968a-a29406c78021.png)

如图先添加应用(也就是创建appkey，自动生成Client ID、ClientSecret)，添加成功后在应用列表中可以看到应用信息，点击查看可以看到自己的appkey、Client ID、ClientSecret，用于SDK的初始化。

### 获取声网 appId, appCert

如果您有声网 Console 后台账号并创建过项目，请先登录声网 Console  后台，点击[这里](https://sso.agora.io/cn/login/)，然后到"项目列表" -> 找到自己的项目点击"编辑"图标后，即可看到 App ID、APP 证书。

如果您没有声网Console后台账号，请先注册账号，点击[这里](https://sso.agora.io/cn/v4/signup)，注册成功后按照步骤1操作。


## 使用

[EMService](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/EMService.html) 是所有API的入口，可以这样初始化：

### 1. 使用 Easemob App Credentials 的情况
``` java
建议写到配置类中，示例如下：
@Configuration
public class Config {

    @Bean
    public EMService service() {
        
        EMProperties properties = EMProperties.builder()
                .setAppkey("Appkey")
                .setClientId("Client ID")
                .setClientSecret("ClientSecret")
                .build();

        return new EMService(properties);
    }
}
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
- [Token](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/token/TokenApi.html)
  用于获取用户Token
- [Room](https://easemob.github.io/easemob-im-server-sdk/com/easemob/im/server/api/room/RoomApi.html)
  用于管理聊天室

每个业务资源对应一个方法，例如，用户相关的API，都可以在.user()找到。

举个例子，我们要注册一个用户，就可以这样写：

``` java
EMService service;
service.user().create(username, password).block();
```

API的返回值是响应式的，如果希望阻塞，可以使用上面例子中的block()。

## 参考

SDK的api文档在[这里](https://easemob.github.io/easemob-im-server-sdk/) 。

## 常见问题
如果你想看Server SDK的请求与响应，可以在配置文件中添加:
``` java
logging.level.com.easemob.im.http=debug
```

## 注意事项

Server SDK是对环信IM [服务端API](http://docs-im.easemob.com/im/server/ready/intro) 的封装，但并没有封装所有的API，只封装了开发者常用的API。

对于注册环信id的规则，Server SDK有自己的限制, 正则为^[a-z][0-9a-z-]{1,32}$，这点与 [官网文档](https://docs-im.easemob.com/im/server/ready/user#%E7%8E%AF%E4%BF%A1_id_%E4%BD%BF%E7%94%A8%E8%A7%84%E5%88%99) 中说明的环信id规则是有区别的，这样做是因为目前环信id注册的限制范围比较广，Server SDK考虑缩小环信id注册的限制范围使其更加规范，在此说明一下。

如果不想使用server sdk注册环信id的限制，可以在初始化配置时添加 'turnOffUserNameValidation()' （sdk需要使用0.3.5以上的版本）。

强烈建议不要使用纯数字或者有规律的字符串来注册环信id，否则自己的用户可能会遭受到攻击、垃圾消息等后果。
``` java
EMProperties properties = EMProperties.builder()
        .setAppkey("appkey")
        .setClientId("Client ID")
        .setClientSecret("ClientSecret")
        .turnOffUserNameValidation()
        .build();
```


