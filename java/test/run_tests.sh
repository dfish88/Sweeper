#!/bin/bash

parentdir=$(dirname $PWD)
classdir=$parentdir/classes
testclassdir=$PWD/classes

java -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$classdir:$testclassdir org.junit.runner.JUnitCore TileTest TimerTest MineFieldTest BoardTest
