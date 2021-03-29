#!/usr/bin/env bash

version=0.2.5

easemob_home="${HOME}/.easemob"
config_file=$easemob_home/"config.properties"
jar_name=im-sdk-cli.jar

echo Begin installing Easemob SDK IM CLI v$version

if [ ! -d $easemob_home  ]; then
  echo "Create dir $easemob_home."
  mkdir $easemob_home
else
  echo "$easemob_home exists, skip creating dir."
fi

if [ ! -f $config_file ]; then
  echo "Create file ${config_file}."
  touch $config_file
  echo -e "# You can get configs from https://console.easemob.com/\nim.appkey=\nim.client-id=\nim.client-secret=" > ~/.easemob/config.properties
else
  echo "$config_file exists, skip creating file."
fi

if [ -f $easemob_home/$jar_name ]; then
  echo "$easemob_home/$jar_name exists, skip downloading."
else
  echo "downloading $jar_name."
  curl -fL -o $easemob_home/$jar_name https://repo1.maven.org/maven2/com/easemob/im/im-sdk-cli/$version/im-sdk-cli-$version.jar
fi

if [ $SHELL = "/bin/bash" ]; then
    profile_file="${HOME}/.bashrc"
fi

if [ $SHELL = "/bin/zsh" ]; then
  profile_file="${HOME}/.zshrc"
fi

if ! grep -q "IM SDK CLI" $profile_file; then
  echo appending command to $profile_file
  cat << EOF >> $profile_file
# IM SDK CLI
alias im='java -jar $easemob_home/$jar_name'
EOF
fi