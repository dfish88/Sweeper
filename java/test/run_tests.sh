#!/bin/bash

parentdir=$(dirname $PWD)
srcdir=$parentdir/src/

javac -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$srcdir TileTest.java BoardTest.java
echo "Testing Tile class"
java -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$srcdir org.junit.runner.JUnitCore TileTest
echo "Testing Board class"
java -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$srcdir org.junit.runner.JUnitCore BoardTest

