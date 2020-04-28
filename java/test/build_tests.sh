#!/bin/bash

parentdir=$(dirname $PWD)
classdir=$parentdir/classes
srcdir=$parentdir/src

#javac -Xlint:deprecation -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$srcdir TimerTest.java TileTest.java BoardTest.java MineFieldTest.java
javac -Xlint:deprecation -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$classdir:$srcdir TileTest.java
