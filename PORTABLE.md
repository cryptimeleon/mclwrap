# Compiling portable library
This file describes how to compile mcl in a way that it runs on most CPUs without issue. 
mclwrap comes with a precompiled portable version of mcl, which is automatically used if mcl is not installed on the system.
So for most users, these instructions will never be necessary to follow. Either compile mcl for your specific system (see [README.md](README.md)), which is recommended for performance reasons, or simply allow mclwrap to fallback to the packaged binaries.

However, internally for the mclwrap project, if we update mcl versions, we also need to update the precompiled portable libraries packaged with mclwrap. These instructions show how. 

## Linux - Portable Library
This describes how to compile the mcl library and Java bindings for maximum portability across all kinds of x86 and x86_64 CPUs. This is the exact way the included Linux .so-s were created.  
Unfortunately, support for non-glibc distributions cannot be assumed (e.g. musl libc in Alpine could not provide all required symbols). However, the same build process works for these platforms as well.
The `scripts/install_portable_mcljava_linux.sh` should handle this automatically if you have the following prerequisites:
 - An install of an old release of a Linux distribution that uses glibc, e.g. Debian Jessie
 - packages build-essential, gcc-multilib, g++-multilib, libssl-dev or equivalent installed
 - [CMake](https://github.com/KitWare/CMake) version 3.22 or newer installed or built from source
 - You have an x86 and an x64 JDK installed. I tested with OpenJDK 11.0.13 from [Adoptium](https://adoptium.net/releases.html?variant=openjdk11). You can also only use a 64 or a 32 bit JDK, but in this case you cannot test the library for the missing architecture. 

The parameters of the script are the top-level directories of the JDKs (where bin, include etc. live) and an output directory for the created DLLs, in order x86-JDK, x64-JDK, output directory. Make sure to specify absolute paths for each parameter. 

## Windows - Portable Library
This describes how to compile the mcl library and Java bindings for maximum portability across all kinds of x86 and x86_64 CPUs. This is the exact way the included Windows DLLs were created.  
The `scripts\install_portable_mcljava_windows.ps1` should handle this automatically if you have the following prerequisites:
 - Execution of PowerShell Scripts is enabled. For this, run `Set-ExecutionPolicy RemoteSigned -Scope CurrentUser` in an Administrator PowerShell prompt. You may want to set this back to `Restricted` when you are finished.
 - You have Visual Studio installed (I tested with 2019) and use a Developer Powershell prompt.
 - You have [CMake](https://cmake.org/download/) and [Git](https://git-scm.com/download/win) installed and the binaries are in the PATH.
 - You have an x86 and an x64 JDK installed. I tested with OpenJDK 11.0.13 from [Adoptium](https://adoptium.net/releases.html?variant=openjdk11).

The parameters of the script are the top-level directories of the JDKs (where bin, include etc. live) and an output directory for the created DLLs. Make sure to specify absolute paths for each parameter. 
