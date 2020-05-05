#!/bin/bash

parentdir=$(dirname $PWD)
classdir=$parentdir/classes

mkdir classes
javac -Xlint:deprecation -d $PWD/classes -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:$classdir $PWD/src/*.java
