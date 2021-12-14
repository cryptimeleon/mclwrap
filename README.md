![Build Status](https://github.com/cryptimeleon/mclwrap/actions/workflows/dev-ci.yaml/badge.svg)
![Build Status](https://github.com/cryptimeleon/mclwrap/actions/workflows/dev-embedded-mcl-ci.yaml/badge.svg)
![Build Status](https://github.com/cryptimeleon/mclwrap/actions/workflows/main-ci.yaml/badge.svg)
![Build Status](https://github.com/cryptimeleon/mclwrap/actions/workflows/scheduled-main-ci.yaml/badge.svg)
# Mclwrap

Mclwrap provides a wrapper around the BN-254 bilinear group implemented in the [MCL library](https://github.com/herumi/mcl). As the bilinear groups implemented in the Cryptimeleon Math library are not particulary efficient, use of this wrapper is recommended for proper benchmarks.
Specifically, the Mclwrap implementation's group operations are roughly 100 times as fast as our own implementation.

## Security Disclaimer
**WARNING: This library is meant to be used for prototyping and as a research tool *only*. It has not been sufficiently vetted for use in security-critical production environments. All implementations are to be considered experimental.**

## Table Of Contents

* [Quickstart Guide](#quickstart)
    * [Adding Maven Dependency](#adding-mclwrap-dependency-with-maven)
    * [Adding Gradle Dependency](#adding-mclwrap-dependency-with-gradle)
* [Compiling mcl on Linux/macOS](#compiling-mcl-on-linux-or-macos)
* [Compiling mcl on Windows](#compiling-mcl-on-windows)
* [Miscellaneous Information](#miscellaneous-information)

## Quickstart

Simply add mclwrap as a dependency (see below for [maven](#adding-mclwrap-dependency-with-maven) and [gradle](#adding-mclwrap-dependency-with-gradle) snippets) and start using the `MclBilinearGroup` in your code. 

For full performance, you should compile the underlying mcl library for your system yourself (by default, a precompiled maximum-compatibility version of mcl is used, which may miss out on some modern processor features).
Instructions for this can be found below. 

### Adding Mclwrap Dependency With Maven
To add the newest Mclwrap version as a dependency, add this to your project's POM:

```xml
<dependency>
    <groupId>org.cryptimeleon</groupId>
    <artifactId>mclwrap</artifactId>
    <version>[3.0,)</version>
</dependency>
```

### Adding Mclwrap Dependency With Gradle

Mclwrap is published via Maven Central.
Therefore, you need to add `mavenCentral()` to the `repositories` section of your project's `build.gradle` file.
Then, add `implementation group: 'org.cryptimeleon', name: 'mclwrap', version: '3.+'` to the `dependencies` section of your `build.gradle` file.

For example:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation group: 'org.cryptimeleon', name: 'mclwrap', version: '3.+'
}
```

## Compiling mcl on Linux or macOS
This is optional, but strongly recommended for full performance on modern CPUs.

You can peform most of the installation automatically by using the [scripts/install_fast_mcljava_linux_mac.sh](scripts/install_fast_mcljava_linux_mac.sh) script contained in this repository. 
It will compile the mcl library (version v1.28) as well as the Java bindings, and move the shared library to the correct library folder.
As a prerequisite, you need to have the `libgmp-dev` package (i.e. libgmp and the corresponding headers) installed.
You will also need `make` and `g++` (or `clang++` if using FreeBSD or OpenBSD).

The [scripts/install_fast_mcljava_linux_mac.sh](scripts/install_fast_mcljava_linux_mac.sh) script takes the `include` path of your Java JDK as its only argument. 
The path should be given without a trailing forward slash.

## Compiling mcl on Windows
This is optional, but strongly recommended for full performance on modern CPUs.

As prerequisites you need Visual Studio with C++ build tools and the Windows 10 SDK installed.
These should be easily installable using the setup application that comes with Visual Studio, which you can access by going to the program deinstallation settings in Windows and then selecting modify under Visual Studio.
Then select the C++ workload and deselect everything but the C++ build tools and the Windows 10 SDK.
Furthermore, you need a Java SDK Version 8 or higher as well as the Git command line application (or some other way to clone git repos).

I did this using Visual Studio 2019 Community Edition and Oracle JDK Version 15 on Windows 10.

The below commands are executed in the Developer Command Prompt for VS 2019 since this allows using the C++ build tools without having to manipulate PATH.

Before you start with the actual installation, you need to make sure that x64 is selected as target architecure in the Command Prompt.
For VS 2017 or later, this is done by executing `vcvarsall.bat x64` in the `C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\` folder (replace with your Visual Studio location). For VS 2015, the batch file will be in `C:\Program Files (x86)\Microsoft Visual Studio 15.0\VC\` instead.

Now, we need to clone [Mcl](https://github.com/herumi/mcl) and [Cybozu_ext](https://github.com/herumi/cybozulib_ext). The repositories need to be in the same folder. Furthermore, we need to ensure that the correct version of Mcl is checked out. We currently use version v1.28.
```
git clone https://github.com/herumi/mcl.git
git clone https://github.com/herumi/cybozulib_ext.git
cd mcl
git checkout v1.28
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

## Miscellaneous Information

- Official Documentation can be found [here](https://cryptimeleon.org).
- Mclwrap adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
- The changelog can be found [here](CHANGELOG.md).
- A description of how we have built the packaged precompiled mcl library can be found [here](PORTABLE.md).
- Mclwrap is licensed under Apache License 2.0, see [LICENSE file](LICENSE).
