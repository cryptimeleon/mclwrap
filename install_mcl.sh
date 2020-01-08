#!/bin/sh
mcl_version="v1.03"

echo "----- Installing libgmp-dev -----"
sudo apt-get update
sudo apt-get -y install libgmp-dev # requirement for mcl
echo "----- Cloning mcl from git://github.com/herumi/mcl -----"
git clone git://github.com/herumi/mcl
git checkout $mcl_version
echo "----- Building mcl $(mcl_version) -----"
cd mcl || exit
make -j4 # build mcl library
echo "----- Building mcl java bindings -----"
ls /usr/lib/jvm/
cd ffi/java || exit
make test_mcl JAVA_INC=$JAVA_INC # build java bindings, set include manually
cd ../..
echo "----- Copying mcl java shared library to /usr/lib/ -----"
sudo cp lib/libmcljava.so /usr/lib/libmcljava.so
echo "----- Installation finished successfully. -----"