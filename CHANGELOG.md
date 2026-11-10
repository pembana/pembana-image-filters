# Pembana Image Filters changelog

## Unreleased

The project now lives under the Pembana organization.

### ⚠️ Upgrading

- [`67fd9d0`](https://github.com/pembana/pembana-image-filters/commit/67fd9d0c1da08e7085dbd17082e7ecba477541e5) Update Git remotes to `pembana/pembana-image-filters` and use the Maven
  coordinates `com.pembana.imaging:pembana-image-filters`.
- [`6b5a0c6`](https://github.com/pembana/pembana-image-filters/commit/6b5a0c6dd26e2be22d49fc03783f41e6f9a8b365) Update filter source headers and project metadata to reflect the MIT license.

### ⭐ New Features

- [`c271b03`](https://github.com/pembana/pembana-image-filters/commit/c271b03093d3b2287cb8671c6f0134a607865fd4) Add named blend modes and improve Javadoc for filters and composites.
- [`a4535a3`](https://github.com/pembana/pembana-image-filters/commit/a4535a3ee6592714a926b73817e88040146b579b) Add getters for the axis and angle in `AxisAngle4f`.

### 📔 Documentation

- [`0fb6ca9`](https://github.com/pembana/pembana-image-filters/commit/0fb6ca90f98f22f989c5639e5d890351d84b9cb3) Document Maven and Gradle setup for releases and snapshots.

### 🧪 Tests

- [`5de5f29`](https://github.com/pembana/pembana-image-filters/commit/5de5f291c6a4808117d7917d33b79a122668ef3f) Add image output tests for the cellular filter.
- [`4685227`](https://github.com/pembana/pembana-image-filters/commit/46852274eadf3a9b074d66d3a992df6e3630f995) Add image output tests for the caustics filter.
- [`4b9ccee`](https://github.com/pembana/pembana-image-filters/commit/4b9ccee6cbeda03459b86df3e73503eb89577f3a) Add image output tests for the water filter.

### 🔨 Dependency Upgrades

- [`0363788`](https://github.com/pembana/pembana-image-filters/commit/0363788c203657fd4f717c1a871ba9958a359b15) Upgrade AssertJ to 3.27.7.
- [`67d5c34`](https://github.com/pembana/pembana-image-filters/commit/67d5c34fbc73c2abb8080f533d3cada1d90325bf) Upgrade JUnit Jupiter Engine to 6.0.3.
- [`d1e874a`](https://github.com/pembana/pembana-image-filters/commit/d1e874ae58a98d467136d8790d65fdcfda3e1ee6) Upgrade JReleaser to 1.27.0 and the Versions Maven Plugin to 2.23.0 as part of aligning the release workflow with Pembana Search.

## 1.0.0-M1

- First milestone release.
