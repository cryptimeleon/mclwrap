# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [3.0.0] 

### Changed
- Replace getInteger with asInteger [#30](https://github.com/cryptimeleon/mclwrap/pull/30/commits/5bcc7a64b1550c33d889f02b14d44f5bdcf014cb)
- Increment Math dependency version to 3.0.0

## [2.0.0] - 2021-06-29

### Fixed

- Fixed serialization of `MclBilinearGroup` returning "bn256" instead of the correct "bn254" which lead to deserialization not working

### Changed
- Made most of the mcl wrapper classes package-private

## [1.0.0] - 2021-03-19

### Added
- Initial release


[Unreleased]: https://github.com/cryptimeleon/mclwrap/compare/v3.0.0...HEAD
[3.0.0]: https://github.com/cryptimeleon/mclwrap/compare/v2.0.0...v3.0.0
[2.0.0]: https://github.com/cryptimeleon/mclwrap/compare/v1.0.0...v2.0.0
[1.0.0]: https://github.com/cryptimeleon/mclwrap/releases/tag/v1.0.0

