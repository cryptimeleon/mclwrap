This is a wrapper for [upb.crypto](https://github.com/upbcuk/upb.crypto.math) for the excellent mcl pairing library: https://github.com/herumi/mcl

To use it, you need to compile the mcl library and make it accessible into one of the paths that JNI will look into at runtime (those locations are printed to the console whenever the wrapper is loaded but fails to locate the library)
