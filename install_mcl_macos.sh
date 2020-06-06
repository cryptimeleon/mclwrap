#!/bin/sh
mcl_version="v1.03"

# check that JAVA_INC is given
if [ $# -eq 0 ]; then
	echo "Missing Java include argument"
	echo "Please give path of Java include folder as first argument"
	echo "For example: /Library/Java/JavaVirtualMachines/openjdk-13.0.1.jdk/Contents/Home/include"
	exit 1
fi

java_inc=$1

echo "----- Cloning mcl from git://github.com/herumi/mcl -----"
git clone git://github.com/herumi/mcl
git checkout $mcl_version
echo "----- Building mcl $mcl_version -----"
(
  cd mcl || exit
  make -j4 || exit # build mcl library
  echo "----- Building mcl java bindings and running tests -----"
  echo "----- Java include path: $java_inc -----"
  cd ffi/java || exit
  make test_mcl JAVA_INC=-I$java_inc || exit # build java bindings, set include manually
  cd ../..
  echo "----- Copying mcl java shared library to /usr/lib/ -----"
	mkdir -p ~/Library/Java/Extensions/ #check that this is included here: System.out.println(System.getProperty("java.library.path"));
	cp lib/libmcljava.dylib ~/Library/Java/Extensions/
)
echo "----- Installation finished successfully. Deleting mcl repository folder -----"
rm -rf mcl
echo "----- Done -----"
