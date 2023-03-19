#!/bin/sh

# taiye 0319
echo "start jar"
java -javaagent:pyroscope.jar -jar /demo/demo-all.jar
