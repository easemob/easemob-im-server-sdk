# Easemob IM CLI

**Easemob IM CLI**是基于**Easemob IM Java SDK**开发的控制台项目，旨在为开发者提供方便快捷的命令行界面来调用服务端API，同时也是**Easemob IM Java SDK**的使用示例。

## 快速开始

### 安装

#### 1. 自动安装

(1) 执行安装脚本
```bash
sh -c "$(curl -fsSL https://raw.githubusercontent.com/easemob/easemob-im-server-sdk/master/im-sdk-cli/install.sh)"
```

或：

```bash
sh -c "$(wget https://raw.githubusercontent.com/easemob/easemob-im-server-sdk/master/im-sdk-cli/install.sh -O -)"
```

(2) 添加alias

向`~/.zshrc`或`~/.bashrc`中添加以下命令(取决于您使用的SHELL)：

```bash
alias im='java -jar ~/.easemob/im-sdk-cli.jar'
source ~/.easemob/completion.sh
```

(3) 刷新配置

```bash
source ~/.zshrc 或 ~/.bashrc
```

这将会将程序安装至`~/.easemob/`中，并将相关命令加入系统环境变量

#### 2. 手动安装

(1) 编译打包
```bash
git clone https://github.com/easemob/easemob-im-server-sdk.git
cd /easemob-im-server-sdk
mvn package -Dmaven.test.skip=true
mkdir ~/.easemob
cp im-sdk-cli/target/im-sdk-cli-x.x.x.jar ~/.easemob/im-sdk-cli.jar
cp im-sdk-cli/target/completion.sh ~/.easemob/completion.sh
```

(2) 添加alias

向`~/.zshrc`或`~/.bashrc`中添加以下命令(取决于您使用的SHELL)：

```bash
alias im='java -jar ~/.easemob/im-sdk-cli.jar'
source ~/.easemob/completion.sh
```

(3) 刷新配置

```bash
source ~/.zshrc 或 ~/.bashrc
```

### 配置

IM CLI 会从`~/.easemob/config.properties`中读取配置文件，您需要从[环信通讯云管理后台](https://console.easemob.com/)来获取您的应用开发者密钥并填写到此处。

```properties
vim ~/.easemob/config.properties

im.appkey=your-appkey
im.client-id=your-app-client-id
im.client-secret=your-app-client-secret
```

### 体验

```bash
# 创建一个用户，用户名为test-user，密码为test-password
❯ im create user test-user test-password

done
```

```bash
# 修改用户名为test-user的登录密码
❯ im update user test-user --password=new-password

done
```

```bash
# 获取用户名为test-user的用户信息
❯ im get user test-user                 

username: test-user
canLogin: true
```

```bash
# 删除用户名为test-user的用户
❯ im delete user test-user

done
```

## 使用方法

### 1. 入口命令

`im`是入口命令，输入`im -h	`查看帮助文档

~~~bash
# 查看入口命令帮助
❯ im -h

Usage: im [-hvV] [COMMAND]
  -h, --help      Show this help message and exit.
  -v, --verbose
  -V, --version   Print version information and exit.
Commands:
  create  Create a resource.
  get     Get a resource.
  delete  Delete a resource.
  update  Update a resource.
~~~

从提示中可知，`im`命令采用面向资源的操作方式，由`create`、`get`、`delete`、`update`四个子命令来表示对资源的`添加`、`查询`、`删除`和`更新`。

### 2. 操作

> 提示：您可在任意命令后输入`-h`或`--help`来查看详细使用帮助，如支持的操作、可操作的资源、命令参数等。

```bash
# 查看可查询的资源
❯ im get -h

Usage: im get [COMMAND]
Get a resource.
Commands:
  admin       List admin
  attachment  Download attachment by id.
  block       List blocked user.
  contact     List contacts of a user.
  group       Get a group's info or list groups
  member      List group or room members, not include the owner and admins.
  message     List or count messages.
  room        Get a room's info or list rooms
  session     Get specific user's online status.
  user        Get a user's info or list users.
```

### 3. 资源

```bash
# 查看创建用户所需的参数
❯ im create user -h                              

Usage: im create user [-hV] <arg0> <arg1>
Create a user.
      <arg0>      the username
      <arg1>      the password
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
```

#### 资源解释

- user：用户
- group：群组
- room：聊天室
- member：群组/聊天室成员
- block：黑名单、禁言
- message：消息
- contact：好友
- attachment：附件
- admin：群组/聊天管理员、超级管理员
- session：用户在线状态

## 完成度

目前CLI支持了常用的API，后续会持续完善。

如果您在使用时发生了错误，或者对此项目有一些建议，欢迎您在Issue中与我们讨论，您的反馈对我们至关重要。