This is a wrapper for [upb.crypto](https://github.com/upbcuk/upb.crypto.math) for the excellent mcl pairing library (v1.26): https://github.com/herumi/mcl

# Installation

To use the wrapper, you need to compile the mcl library as well as the Java bindings, and copy the latter to one of the paths that JNI will search at runtime (those locations are printed to the console whenever the wrapper is loaded but fails to locate the library).
We give a more detailed tutorial below.

# Linux/Mac OS

You can peform most of the installation automatically by using the `install_mcl.sh` script contained in this directory. 
It will compile the mcl library (version v1.26) as well as the Java bindings, and move the shared library to the correct library folder.
As a prerequisite, you need to have the `libgmp-dev` package installed.
Additionally, you may have to make the script executable by executing `chmod +x install_mcl.sh` before execution.

# Windows

As prerequisites you need Visual Studio with C++ build tools and the Windows 10 SDK installed.
These should be easily installable using the setup application that comes with Visual Studio, which you can access by going to the program deinstallation settings in Windows and then selecting modify under Visual Studio.
Then select the C++ workload and deselect everything but the C++ build tools and the Windows 10 SDK.
Furthermore, you need a Java SDK Version 8 or higher as well as the Git command line application (or some other way to clone git repos).

I did this using Visual Studio 2019 Community Edition and Oracle JDK Version 15 on Windows 10.

The below commands are executed in the Developer Command Prompt for VS 2019 since this allows using the C++ build tools without having to manipulate PATH.

Before you start with the actual installation, you need to make sure that x64 is selected as target architecure in the Command Prompt.
For VS 2017 or later, this is done by executing `vcvarsall.bat x64` in the `C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\` folder (replace with your Visual Studio location). For VS 2015, the batch file will be in `C:\Program Files (x86)\Microsoft Visual Studio 15.0\VC\` instead.

Now, we need to clone [Mcl](https://github.com/herumi/mcl) and [Cybozu_ext](https://github.com/herumi/cybozulib_ext). The repositories need to be in the same folder. Furthermore, we need to ensure that the correct version of Mcl is checked out. We currently use version v1.26.
```
git clone https://github.com/herumi/mcl.git
git clone https://github.com/herumi/cybozulib_ext.git
cd mcl
git checkout v1.26
```

Next, we build Mcl:
```
mklib
mk -s test\bls12_test.cpp && bin\bls12_test.exe
```
This will build Mcl as a static library and run some tests.
If you get a `module machine type 'X654' conflicts with target machine type 'x86'` error, you probably forgot to execute `vcvarsall.bat x64` during the first step.
If `c1` or `lib` cannot be found, you don't have the C++ build tools installed or they cannot be found.
If the compiler complains about missing headers, you are probably missing the Windows 10 SDK.

If this succeeds, we can move on to building the java bindings. This will require you to set the `JAVA_HOME` variable to your Java SDK installation folder.
```
cd ffi\java
set JAVA_HOME=C:\PROGRA~1\Java\jdk-15
make_wrap
```
If you are wondering what is going with that `JAVA_HOME` path, the script seems to not like spaces in folder names so we are using DOS folder names which don't contain spaces.
You can find out the DOS folder names by executing `dir /X`.

Lastly, we need to move the compiled DLL to the correct path. 
You can find it under `mcl\bin\mcljava.dll`.
The target path should be printed in the console when running the Mclwrap tests (the first exception thrown).
For me this was `C:\Users\<User>\.jdks\openjdk-15\bin`.
