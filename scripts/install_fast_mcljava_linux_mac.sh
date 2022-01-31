#!/usr/bin/env bash
mcl_version="45f4504244f535a55cd5b2d19bf01eee86094cf3"
# exit immediately on error
set -e

GMP_VERSION=6.2.1
gmp_from_source(){
 echo "Cloning GNU gmp."
 curl https://gmplib.org/download/gmp/gmp-${GMP_VERSION}.tar.xz > gmp-${GMP_VERSION}.tar.xz || (echo "Error downloading GMP" && exit 1)
 unxz gmp-${GMP_VERSION}.tar.xz || (echo "Could not extract .xz archive - make sure you have unxz installed." && exit 1)
 tar -xvf gmp-${GMP_VERSION}.tar || (echo "Could not untar the gmp tree." && exit 1)
 rm gmp-${GMP_VERSION}.tar
 cd gmp-${GMP_VERSION}
 ./configure --enable-shared --enable-static --enable-cxx || (echo "Could not determine a GMP configuration" && exit 1)
 make -j $(nproc) || (echo "Could not make GMP" && exit 1)
 #make check || (echo "Tests for GMP failed!" && exit 1)
 sudo make install || (echo "Could not install gmp to /usr/" && exit 1)
 cd ..
 rm -r gmp-${GMP_VERSION}
}
# check for operating system
os=""
if [ "$(uname)" == "Darwin" ]; then
  os="mac"
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
  os="linux"
else
  echo "Unsupported operating system. This script only works on Linux and macOS."
  exit 2
fi

# check that JAVA_INC is given
if [ $# -eq 0 ]; then
	echo "Missing Java include argument"
	echo "Please specify path of your JDK 'include' directory as first argument"
	if [ $os == "linux" ]; then
    echo "For example: ./install_fast_mcljava_linux_mac.sh /usr/lib/jvm/java-8-openjdk-amd64/include"
  else # mac os
    echo "For example: ./install_fast_mcljava_linux_mac.sh /Library/Java/JavaVirtualMachines/openjdk-13.0.1.jdk/Contents/Home/include"
    echo "For your system, it's probably: "
    javahome=$(/usr/libexec/java_home)
    echo ./install_fast_mcljava_linux_mac.sh $javahome/include
  fi
	exit 1
fi

if [ -z ${2+x} ]; then
	echo "Skipping from-source gmp install. Re-run this script with a second parameter to clone and build gmp from source.";
else
	echo "Building gmp from source for optimal performance.";
	cd /tmp
	gmp_from_source
fi

java_inc=$1

(
  echo "----- Cloning mcl from https://github.com/WorldofJARcraft/mcl.git -----"
  cd /tmp
  git clone https://github.com/herumi/mcl.git
  cd mcl
  git checkout $mcl_version || exit
  echo "----- Deleting currently installed version of mcl -----"
  if [ $os == "linux" ]; then
    sudo rm /usr/lib/libmcljava.so
  else # mac os
    mkdir -p ~/Library/Java/Extensions/ #check that this is included here: System.out.println(System.getProperty("java.library.path"));
	  rm ~/Library/Java/Extensions/libmcljava.dylib
  fi
  echo "----- Building mcl -----"
  mkdir build 2>/dev/null
  cd build
  cmake .. -DCMAKE_C_FLAGS="-march=native -mtune=native" -DCMAKE_CXX_FLAGS="-march=native -mtune=native" -DMCL_STATIC_LIB="ON" 
  cmake --build .
  cd ..
  export MCL_LIBDIR=$(pwd)/build/lib
  echo "----- Building mcl java bindings and running tests -----"
  echo "----- Java include path: $java_inc -----"
  cd ffi/java
  mkdir build 2>/dev/null
  cd build
  export JAVA_HOME=$1/../
  if [ -z ${2+x} ]; then
	  cmake .. -DMCL_LINK_DIR=${MCL_LIBDIR} -DCMAKE_C_FLAGS="-march=native -mtune=native" -DCMAKE_CXX_FLAGS="-march=native -mtune=native"
  else
	  cmake .. -DMCL_LINK_DIR=${MCL_LIBDIR} -DCMAKE_C_FLAGS="-march=native -mtune=native" -DCMAKE_CXX_FLAGS="-march=native -mtune=native" -DGMP_LINK_DIR=/usr/local/lib
	  echo cmake .. -DMCL_LINK_DIR=${MCL_LIBDIR} -DCMAKE_C_FLAGS="-march=native -mtune=native" -DCMAKE_CXX_FLAGS="-march=native -mtune=native" 
  cmake --build . -v
  fi
  echo "----- Copying mcl java shared library to /usr/lib/ -----"
  if [ $os == "linux" ]; then
    sudo cp libmcljava.so /usr/lib/
  else # mac os
    mkdir -p ~/Library/Java/Extensions/ #check that this is included here: System.out.println(System.getProperty("java.library.path"));
	  cp libmcljava.dylib ~/Library/Java/Extensions/
  fi
  echo "----- Installation finished successfully. Deleting mcl repository folder -----"
  rm -r /tmp/mcl
  echo "----- Done -----"
) || { echo "----- Failed installation. -----"; rm -rf /tmp/mcl; exit 3; }
