#!/bin/bash

parentdir=$PWD
srcdir=$parentdir/src/

#javac -cp .:$srcdir/View:$srcdir/Model:$srcdir/Presenter *.java $srcdir/Presenter/*.java $srcdir/View/*.java $srcdir/Model/*.java
javac -cp $srcdir -d classes/ $srcdir/*/*.java $srcdir/*.java
