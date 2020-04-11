#!/bin/bash

parentdir=$(dirname $PWD)
srcdir=$parentdir/src/

javac -Xlint:deprecation -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$srcdir TimerTest.java TileTest.java BoardTest.java
java -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$srcdir org.junit.runner.JUnitCore TimerTest TileTest BoardTest

