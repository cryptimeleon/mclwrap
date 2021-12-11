Param (
[Parameter(Mandatory=$true,HelpMessage="Top-level dir of x86 JDK (where bin, include, etc. live)")][string]$JDK86,
[Parameter(Mandatory=$true,HelpMessage="Top-level dir of x86-64 JDK (where bin, include, etc. live)")][string]$JDK64,
[Parameter(Mandatory=$true,HelpMessage="Where to store the generated DLLs")][string]$Outdir
)

Write-Output "Cloning mcl..."

git clone https://github.com/WorldofJARcraft/mcl mcl-fork
Set-Location mcl-fork
git checkout v1.28-CMake

Write-Output "Building mcl for 32 Bit"

mkdir build
Set-Location build

Remove-Item CMakeCache.txt
Remove-Item -Recurse CMakeFiles

cmake.exe  -A Win32 -DCMAKE_C_FLAGS="/arch:IA32" -DCMAKE_CXX_FLAGS="/arch:IA32" -DMCL_STATIC_LIB=ON  -DMCL_USE_GMP=OFF -DMCL_USE_ASM=OFF -DMCL_USE_XBYAK=OFF ..
cmake --build . --config Release

Write-Output "Built mcl."
Write-Output "Building mcl java ffi for 32 Bit"

Set-Location ..\ffi\java
mkdir build
Set-Location .\build

$env:JAVA_HOME=$JDK86

# get absolute path of mcl's build dir
$MCL_Libdir=[System.IO.Path]::Combine($pwd, "..\..\..\build\lib\Release")
$MCL_Libdir=Resolve-Path $MCL_Libdir

Write-Output "Looking for mcl lib at $MCL_Libdir"

Remove-Item CMakeCache.txt
Remove-Item -Recurse CMakeFiles

cmake.exe -A Win32 -DCMAKE_C_FLAGS="/arch:IA32" -DCMAKE_CXX_FLAGS="/arch:IA32" -DMCL_LINK_DIR="$MCL_Libdir" -DMCL_USE_XBYAK=OFF  ..
cmake --build . --config Release

Write-Output "Testing Java mcl - make sure the mclwrap test passes (the other one might fail...)"

ctest -C Release

Copy-Item Release/mcljava.dll $Outdir/mcljava-x86.dll

Set-Location ..\..\..\build

Remove-Item CMakeCache.txt
Remove-Item -Recurse CMakeFiles

Write-Output "Building mcl for 64 Bit"

cmake -A x64 -DCMAKE_C_FLAGS="/arch:SSE2" -DCMAKE_CXX_FLAGS="/arch:SSE2" -DMCL_STATIC_LIB=ON  -DMCL_USE_GMP=OFF -DMCL_USE_ASM=OFF -DMCL_USE_XBYAK=OFF ..
cmake --build . --config Release

Write-Output "Built mcl."
Write-Output "Building mcl java ffi for 64 Bit"

Set-Location ..\ffi\java
Set-Location .\build

Remove-Item CMakeCache.txt
Remove-Item -Recurse CMakeFiles

$env:JAVA_HOME=$JDK64

cmake -A x64 -DCMAKE_C_FLAGS="/arch:SSE2" -DCMAKE_CXX_FLAGS="/arch:SSE2" -DMCL_LINK_DIR="$MCL_Libdir" -DMCL_USE_XBYAK=OFF ..
cmake --build . --config Release
Write-Output "Testing Java mcl - make sure the mclwrap test passes (the other one might fail...)"

ctest -C Release

Copy-Item Release/mcljava.dll $Outdir/mcljava-x64.dll

Set-Location ..\..\..\..