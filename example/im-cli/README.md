# Easemob IM CLI

This is an example project to demonstrate usage of the Easemob IM Java SDK.

## Configuration

## Auto Completion

To setup auto completion:

```
alias im='java -jar im-cli-0.0.1.jar'
. completion.sh
```


## Commands

- User
- ...

### User Command Usage

To list 10 users:

``` bash
im user list -l=10 
```

To create a user:

``` bash
im user create username password
```

To delete a user:

``` bash
im user delete username
```

To reset a user's password:

``` bash
im user reset-password username password
```

To get user's info:
``` bash
im user get username
```