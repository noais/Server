#!/bin/bash
CP=target/server-1.0-SNAPSHOT-jar-with-dependencies.jar:$CLASSPATH
MEMORY=512M
JAVA_COMMAND="java -Xmx$MEMORY -Dfile.encoding=UTF-8 -classpath $CP"
CLASS=com.test.server.Main

$JAVA_COMMAND $CLASS $*
