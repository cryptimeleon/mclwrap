#!/bin/sh
mcl_version="v1.03"
# exit immediately on error
set -e

# check that JAVA_INC is given
if [ $# -eq 0 ]; then
	echo "Missing Java include argument"
	echo "Please give path of Java include folder as first argument"
	echo "For example: /usr/lib/jvm/java-8-openjdk-amd64/include"
	exit 1
fi

java_inc=$1

(
  echo "----- Cloning mcl from git://github.com/herumi/mcl -----"
  cd /tmp
  git clone git://github.com/herumi/mcl
  cd mcl
  git checkout $mcl_version || exit
  echo "----- Building mcl $mcl_version -----"
  make -j4 || exit # build mcl library
  echo "----- Building mcl java bindings and running tests -----"
  echo "----- Java include path: $java_inc -----"
  cd ffi/java
  make test_mcl JAVA_INC=-I$java_inc || exit # build java bindings, set include manually
  echo "----- Copying mcl java shared library to /usr/lib/ -----"
  cd ../..
  sudo cp lib/libmcljava.so /usr/lib/
  echo "----- Installation finished successfully. Deleting mcl repository folder -----"
  cd ..
  rm -rf mcl
  echo "----- Done -----"
) || rm -rf /tmp/mcl
