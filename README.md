This is a wrapper for [upb.crypto](https://github.com/upbcuk/upb.crypto.math) for the excellent mcl pairing library: https://github.com/herumi/mcl

# Installation

To use the wrapper, you need to compile the mcl library and make it accessible into one of the paths that JNI will look into at runtime (those locations are printed to the console whenever the wrapper is loaded but fails to locate the library).

# Linux/Mac OS

You can peform most of the installation automatically by using the `install_mcl.sh` script contained in this directory. 
It will compile the mcl library (version v1.03) as well as the Java bindings, and move the shared library to the correct library folder.
As a prerequisite, you need to have the `libgmp-dev` package installed.
Additionally, you may have to make the script executable by executing `chmod +x install_mcl.sh` before execution.
