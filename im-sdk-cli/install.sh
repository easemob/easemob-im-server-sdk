#!/usr/bin/env bash

version=0.2.5

easemob_home="${HOME}/.easemob"
config_file=$easemob_home/"config.properties"
jar_name=im-sdk-cli.jar

echo Begin installing Easemob SDK IM CLI v$version

if [ ! -d $easemob_home ]; then
  echo "Create dir $easemob_home."
  mkdir $easemob_home
else
  echo "$easemob_home exists, skip creating dir."
fi

if [ ! -f $config_file ]; then
  echo "Create file ${config_file}."
  touch $config_file
  echo -e "# You can get configs from https://console.easemob.com/\nim.appkey=\nim.client-id=\nim.client-secret=" >~/.easemob/config.properties
else
  echo "$config_file exists, skip creating file."
fi

echo "downloading $jar_name."
curl -fL -o $easemob_home/$jar_name https://repo1.maven.org/maven2/com/easemob/im/im-sdk-cli/$version/im-sdk-cli-$version.jar

echo "downloading completion.sh."
curl -fL -o $easemob_home/completion.sh https://raw.githubusercontent.com/easemob/easemob-im-server-sdk/master/im-sdk-cli/completion.sh

echo -e "\nAll done!\n"

echo "Please add the following script in your ~/.bashrc or ~/.zshrc:"
echo ""
echo "    alias im='java -jar ~/.easemob/im-sdk-cli.jar'"
echo "    source ~/.easemob/completion.sh"
echo ""
echo "Then issue the following command:"
echo ""
echo "    source ~/.bashrc or ~/.zshrc"
echo "    im -h"
echo ""
echo "Enjoy!!!"
