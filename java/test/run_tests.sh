#!/bin/bash

parentdir=$(dirname $PWD)

export JUNIT_HOME=/usr/local/JUNIT
export CLASSPATH=$CLASSPATH:$JUNIT_HOME/junit-4.13.jar:.
export CLASSPATH=$CLASSPATH:$JUNIT_HOME/hamcrest-core-1.3.jar:.
export CLASSPATH=$CLASSPATH:$parentdir/src/:.

javac TileTest.java BoardTest.java TestSuite.java TestRunner.java
java TestRunner
