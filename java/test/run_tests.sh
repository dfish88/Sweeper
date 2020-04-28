#!/bin/bash

parentdir=$(dirname $PWD)
srcdir=$parentdir/src/

java -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$srcdir org.junit.runner.JUnitCore TimerTest TileTest BoardTest MineFieldTest
