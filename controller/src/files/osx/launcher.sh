#!/bin/sh

NAME=%NAME%
JAVA_MAJOR=1
JAVA_MINOR=%MINOR%
APP_JAR="%JAR%"
APP_NAME="%NAME%"
DISPLAY=%DOCK%

VM_ARGS=""

DIR=$(cd "$(dirname "$0")"; pwd)

ERROR_TITLE="Cannot launch $APP_NAME"
ERROR_MSG="$APP_NAME requires Java version $JAVA_MAJOR.$JAVA_MINOR or later to run."
DOWNLOAD_URL="http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html"

if type -p java; then
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]]; then
    _java="$JAVA_HOME/bin/java"
else
    osascript \
	-e "set question to display dialog \"$ERROR_MSG\" with title \"$ERROR_TITLE\" buttons {\"Cancel\", \"Download\"} default button 2" \
	-e "if button returned of question is equal to \"Download\" then open location \"$DOWNLOAD_URL\""
	echo "$ERROR_TITLE"
	echo "$ERROR_MSG"
	exit 1
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    if [[ "$version" < "$JAVA_MAJOR.$JAVA_MINOR" ]]; then
        osascript \
    	-e "set question to display dialog \"$ERROR_MSG\" with title \"$ERROR_TITLE\" buttons {\"Cancel\", \"Download\"} default button 2" \
    	-e "if button returned of question is equal to \"Download\" then open location \"$DOWNLOAD_URL\""
    	echo "$ERROR_TITLE"
    	echo "$ERROR_MSG"
    	exit 1
    fi
fi

exec $_java $VM_ARGS -Dapple.laf.useScreenMenuBar=$DISPLAY -Duser.dir="$DIR" -Dcom.apple.macos.use-file-dialog-packages=$DISPLAY -Dapple.awt.UIElement=$DISPLAY -Xdock:name="$APP_NAME" -cp ".;$DIR;" -jar "$DIR/$APP_JAR"