#!/bin/sh

echo "start jar"
java -javaagent:pyroscope.jar -jar /demo/demo-all.jar
