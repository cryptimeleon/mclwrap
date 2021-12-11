#!/bin/sh
# required packages: debian 8: build-essential libssl-dev gcc-multilib g++-multilib cmake 3.22 from source

if [ -z ${1+x} ]; then echo Usage: $0 '<Path to 32 Bit JDK> <Path to 64 Bit jdk> <output dir>'; exit 1; fi
if [ -z ${2+x} ]; then echo Usage: $0 '<Path to 32 Bit JDK> <Path to 64 Bit jdk> <output dir>'; exit 1; fi
if [ -z ${3+x} ]; then echo Usage: $0 '<Path to 32 Bit JDK> <Path to 64 Bit jdk> <output dir>'; exit 1; fi

echo Cloning mcl

git clone https://github.com/WorldofJARcraft/mcl mcl-fork
cd mcl-fork
git checkout v1.28-CMake

echo Configuring and building mcl for 64 Bits

mkdir build 2>/dev/null
cd build

rm -r CMakeCache.txt CMakeFiles 2>/dev/null


cmake -DCMAKE_C_FLAGS="-march=athlon64" -DCMAKE_CXX_FLAGS="-march=athlon64" -DCMAKE_SHARED_LINKER_FLAGS="-static-libstdc++ -static-libgcc" -DMCL_STATIC_LIB=ON  -DMCL_USE_GMP=OFF -DMCL_USE_ASM=OFF -DMCL_USE_XBYAK=OFF ..
cmake --build . -v

echo "Building Java FFI for 64 Bits"

cd ../ffi/java

mkdir build 2>/dev/null
cd build

rm -r CMakeCache.txt CMakeFiles 2>/dev/null

MCL_LIBDIR=$(realpath ../../../build/lib)

export JAVA_HOME=$2

cmake -DCMAKE_C_FLAGS="-march=athlon64" -DCMAKE_CXX_FLAGS="-march=athlon64" -DCMAKE_SHARED_LINKER_FLAGS="-static-libstdc++ -static-libgcc" -DMCL_LINK_DIR="$MCL_LIBDIR" -DMCL_USE_XBYAK=OFF ..
cmake --build . -v

echo "Testing Java FFI. We only care about the first test..."

ctest .

cp libmcljava.so $3/libmcljava-amd64.so

cd ../../..

echo Configuring and building mcl for 32 Bits

mkdir build 2>/dev/null
cd build

rm -r * 2>/dev/null


cmake -DCMAKE_C_FLAGS="-m32 -march=i386" -DCMAKE_CXX_FLAGS="-m32 -march=i386" -DCMAKE_SHARED_LINKER_FLAGS="-static-libstdc++ -static-libgcc" -DMCL_STATIC_LIB=ON  -DMCL_USE_GMP=OFF -DMCL_USE_ASM=OFF -DMCL_USE_XBYAK=OFF ..
cmake --build . -v

echo "Building Java FFI for 32 Bits"

cd ../ffi/java

cd build

rm -r CMakeCache.txt CMakeFiles 2>/dev/null

MCL_LIBDIR=$(realpath ../../../build/lib)

export JAVA_HOME=$1

cmake -DCMAKE_C_FLAGS="-m32 -march=i386" -DCMAKE_CXX_FLAGS="-m32 -march=i386" -DCMAKE_SHARED_LINKER_FLAGS="-static-libstdc++ -static-libgcc" -DMCL_LINK_DIR="$MCL_LIBDIR" -DMCL_USE_XBYAK=OFF ..
cmake --build . -v

echo "Testing Java FFI. We only care about the first test..."

ctest .

cp libmcljava.so $3/libmcljava-i386.so

cd ../../..


