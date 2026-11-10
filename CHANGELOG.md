# Pembana Image Filters changelog

## Unreleased

This milestone moves the project to the Pembana organization, adds named
blend modes, and expands filter test coverage. It also updates the build
toolchain and introduces signed releases with a dry-run workflow.

### ⚠️ Upgrading

- [`67fd9d0`](https://github.com/pembana/pembana-image-filters/commit/67fd9d0c1da08e7085dbd17082e7ecba477541e5) Update Git remotes from `ghillert/image-filters-4j` to `pembana/pembana-image-filters`.
- [`6b5a0c6`](https://github.com/pembana/pembana-image-filters/commit/6b5a0c6dd26e2be22d49fc03783f41e6f9a8b365) Replace the Maven coordinates `com.hillert:image-filters-4j` with
  `com.pembana.imaging:pembana-image-filters`. Filter source headers now reflect the MIT license.
- [`c271b03`](https://github.com/pembana/pembana-image-filters/commit/c271b03093d3b2287cb8671c6f0134a607865fd4) Several public fields are now private. Replace direct field access with the corresponding
  methods where available, such as `DitherFilter.setColorDither`, `LightFilter.setNormalEvaluator`,
  and the grid accessors on `WarpGrid`. `MiscComposite.RULE_NAMES` is now private.
- [`5de5f29`](https://github.com/pembana/pembana-image-filters/commit/5de5f291c6a4808117d7917d33b79a122668ef3f) `CellularFilter.Point` and `CellularFunction2D.Point` are now package-private.
  Use `CellularFilter.setDistancePower` and `getDistancePower` in place of direct field access.
- [`e6c37fa`](https://github.com/pembana/pembana-image-filters/commit/e6c37fa7f619f5bd538ad0e06c91b178ccd43358) Use JDK 21 or later to build from source. The library still targets Java 17.

### ⭐ New Features

- [`c271b03`](https://github.com/pembana/pembana-image-filters/commit/c271b03093d3b2287cb8671c6f0134a607865fd4) Add named blend modes and improve Javadoc for filters and composites.
- [`a4535a3`](https://github.com/pembana/pembana-image-filters/commit/a4535a3ee6592714a926b73817e88040146b579b) Add getters for the axis and angle in `AxisAngle4f`.

### 🐞 Bug Fixes

- [`d07622f`](https://github.com/pembana/pembana-image-filters/commit/d07622f90d8819b895152133554f24209f72f2fb) Fix Javadoc warnings caused by the index comparison in `ArrayColormap.setColorInterpolated`.

### 📔 Documentation

- [`0fb6ca9`](https://github.com/pembana/pembana-image-filters/commit/0fb6ca90f98f22f989c5639e5d890351d84b9cb3) Document Maven and Gradle setup for releases and snapshots.
- [`d1e874a`](https://github.com/pembana/pembana-image-filters/commit/d1e874ae58a98d467136d8790d65fdcfda3e1ee6) Document GitHub Actions and command-line publication in the [release guide](docs/releasing.md).

### 🧪 Tests

- [`5de5f29`](https://github.com/pembana/pembana-image-filters/commit/5de5f291c6a4808117d7917d33b79a122668ef3f) Add image output tests for the cellular filter.
- [`4685227`](https://github.com/pembana/pembana-image-filters/commit/46852274eadf3a9b074d66d3a992df6e3630f995) Add image output tests for the caustics filter.
- [`4b9ccee`](https://github.com/pembana/pembana-image-filters/commit/4b9ccee6cbeda03459b86df3e73503eb89577f3a) Add image output tests for the water filter.
- [`c271b03`](https://github.com/pembana/pembana-image-filters/commit/c271b03093d3b2287cb8671c6f0134a607865fd4) Add image output tests for the dither filter.
- [`843eb77`](https://github.com/pembana/pembana-image-filters/commit/843eb77f0c19e3c4dfa4baf4b347d7ae03948c05) Add a runnable skeleton filter demonstration.

### 🔨 Dependency Upgrades

- [`0363788`](https://github.com/pembana/pembana-image-filters/commit/0363788c203657fd4f717c1a871ba9958a359b15) Upgrade AssertJ to 3.27.7.
- [`e6c37fa`](https://github.com/pembana/pembana-image-filters/commit/e6c37fa7f619f5bd538ad0e06c91b178ccd43358) Upgrade JUnit Jupiter Engine to 6.1.3, Checkstyle to 14.4.0, and Spring Java Format Checkstyle to 0.0.48.
- [`7c817a9`](https://github.com/pembana/pembana-image-filters/commit/7c817a996ff83f4f712a0c213cd054ba2439af4a) Upgrade Maven Compiler to 3.16.0, Deploy to 3.2.0, and Enforcer to 3.7.0.
- [`d1e874a`](https://github.com/pembana/pembana-image-filters/commit/d1e874ae58a98d467136d8790d65fdcfda3e1ee6) Upgrade JReleaser to 1.27.0 and the Versions Maven Plugin to 2.23.0.
- [`92ad60f`](https://github.com/pembana/pembana-image-filters/commit/92ad60fdce88282ff58f22f77cd3cd54802fdad0) Upgrade GitHub Actions: Checkout to 7.0.1, Setup Java to 6.2.0, Upload Artifact to 7.0.2, and Download Artifact to 8.0.2.

### ⚙️ Build and Release

- [`3f3a53c`](https://github.com/pembana/pembana-image-filters/commit/3f3a53cec2b3e2c55c3c35352eb58d8008f57809) Run Checkstyle validation as part of the Maven build.
- [`d1e874a`](https://github.com/pembana/pembana-image-filters/commit/d1e874ae58a98d467136d8790d65fdcfda3e1ee6) Use one dry-run-first workflow for snapshots and signed releases.
  Validate the Maven Central bundle before pushing the signed release and snapshot commits and tag.
- [`7c817a9`](https://github.com/pembana/pembana-image-filters/commit/7c817a996ff83f4f712a0c213cd054ba2439af4a) Centralize Maven plugin versions in the POM and use the configured Versions Maven Plugin during release preparation.

## 1.0.0-M1

- First milestone release.
