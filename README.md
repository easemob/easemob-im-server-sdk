# Easemob IM Java SDK

This is the official Easemob IM Java SDK repository.

Easemob provides instant messaging APIs for your applications.

This project provides a facade for our RESTful API so that you can focus on the business logic. 

```
This project is under heavy development, the API will change rapidly until first release. 
```

## Register your app
You will need an appkey and credentials to use this SDK. Get them from  the [Easemob Console](https://console.easemob.com).

## Install the SDK
For Maven, add following dependency in your pom.xml:

```xml
<dependency>
	<groupId>com.easemob.im</groupId>
	<artifactId>im-sdk</artifactId>
	<version>0.0.1</version>
</dependency>
```

## Configure the SDK

``` java
	EMService service = new EMService(
		EMProperties.builder()
			.withBaseUri("https://a1.easemob.com")
			.withAppkey("some-org-id#your-awesome-app")
			.withClientId("the-client-id")
			.withClientSecret("the-client-secret").build());
```

🎉  The SDK is configured, you may start to use it now.

## To use the SDK
The EMService provides asynchronous APIs, for example to register user, and send a welcome message to him, you can:

``` java
	EMService service;
	service.registerUser("bob", "VeryStrongPassword")
		.then(service.sendText("Welcome, bob").toUser("bob"))
		.subscribe();
```

Note subscribe() will return immediately, if you want to block until complete or timeout, you may:

``` java
	EMService service;
	service.registerUser("bob", "VeryStrongPassword")
		.then(service.sendText("Welcome, bob").toUser("bob"))
		.block(Duration.ofSeconds(3));
```

## References

[Wiki]() TBD.

[Javadoc]() TBD.