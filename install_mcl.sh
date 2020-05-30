#!/bin/sh
mcl_version="v1.03"

# check that JAVA_INC is given
if [ $# -eq 0 ]; then
	echo "Missing Java include argument"
	echo "Please give path of Java include folder as first argument"
	echo "For example: /usr/lib/jvm/java-8-openjdk-amd64/include"
	exit 1
fi

java_inc=$1

echo "----- Cloning mcl from git://github.com/herumi/mcl -----"
git clone git://github.com/herumi/mcl
git checkout $mcl_version
echo "----- Building mcl $mcl_version -----"
cd mcl || exit
make -j4 || exit # build mcl library
echo "----- Building mcl java bindings -----"
echo "----- Java include path: $java_inc -----"
cd ffi/java || exit
make test_mcl JAVA_INC=-I$java_inc || exit # build java bindings, set include manually
cd ../..
echo "----- Copying mcl java shared library to /usr/lib/ -----"
sudo cp lib/libmcljava.so /usr/lib/libmcljava.so
echo "----- Installation finished successfully. -----"
