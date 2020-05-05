#!/bin/bash

parentdir=$PWD
srcdir=$parentdir/src/

mkdir classes
javac -cp $srcdir -d classes/ $srcdir/*/*.java $srcdir/*.java
