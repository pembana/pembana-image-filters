# Versioning and Maven publication

Pembana Image Filters follows the same publication process as Pembana Search.
Maven stages the POM, library, sources, and Javadoc under
`target/staging-deploy`. JReleaser signs the artifacts and selects the
destination from the project version.

| Version | JReleaser deployer | Destination |
| --- | --- | --- |
| `*-SNAPSHOT` | `nexus2.snapshot-deploy` | Central Portal snapshots |
| Release, milestone, or RC | `mavenCentral.sonatype` | Maven Central |

## Repository configuration

Verify ownership of `com.pembana.imaging` in Central Portal and create a publishing
token authorized for that namespace. In GitHub, create the `maven-central`
environment and give it the same five secrets as Pembana Search.

| Secret | Value |
| --- | --- |
| `JRELEASER_GPG_PUBLIC_KEY` | Current ASCII-armored public signing key |
| `JRELEASER_GPG_SECRET_KEY` | Matching ASCII-armored private signing key |
| `JRELEASER_GPG_PASSPHRASE` | Signing key passphrase |
| `JRELEASER_NEXUS2_USERNAME` | Central Portal publishing token username |
| `JRELEASER_NEXUS2_PASSWORD` | Central Portal publishing token password |

The workflow passes the same Central token to both deployers. Publish the public
PGP key to a keyserver supported by Maven Central. For full releases, the key
also signs the Git commits and tag. Its primary user ID must be in
`Name <email>` form. Register the key with the GitHub account that has verified
that email so GitHub can verify the signatures.

The push and GitHub release jobs use GitHub's built-in token. Branch and tag
rules must allow it to advance `main` and create the release tag.

## Artifact-only publication

Open **Actions → Maven publication → Run workflow** on the intended revision.
Leave both version fields blank and keep **dry-run** enabled. The workflow
runs Maven CI, stages the artifacts, and prepares the signed deployment. Review
the reports, then dispatch the same revision with **dry-run** unchecked to
publish the version in the POM. On a snapshot revision this publishes a snapshot.

## Full release

1. Update the `## Unreleased` section in [CHANGELOG.md](../CHANGELOG.md) on
   `main`. Start with a short summary and group entries under level-three
   headings such as **⭐ New Features**, **🐞 Bug Fixes**,
   **📔 Documentation**, and **🔨 Dependency Upgrades**. Start each change
   bullet with a linked short commit hash in code formatting, followed by
   the description. The workflow keeps that order in the GitHub release notes
   and skips categories with no entries.
2. Dispatch **Maven publication** on `main` with both versions filled in.
   For `1.0.0-M2`, use `1.0.0-SNAPSHOT` as the next snapshot. For a stable
   `1.0.0` release, choose a later snapshot such as `1.0.1-SNAPSHOT`.
   The current snapshot must have the same base version as the release.
3. Keep **dry-run** enabled for the first run. Review the rendered release
   notes, source revision, Maven CI results, and signed bundle. If `main`
   advances, review a new dry run.
4. Dispatch the same inputs with **dry-run** unchecked. JReleaser uploads the
   signed bundle for validation. After Central validates it, the workflow
   atomically pushes the signed release and next snapshot commits and the
   `v<version>` tag. It then publishes the same Central deployment and posts
   the changelog to GitHub. Milestones and RCs appear as GitHub prereleases.

Use **Re-run failed jobs** to keep the original deployment ID and prepared refs
when a later job fails. If upload fails, check Central Portal for an unpublished
deployment from that attempt before retrying. If `main` advanced before the
atomic push, review the new tip and discard the unused deployment before
starting over. If the push succeeded, keep the signed tag and recover the
original deployment using the ID in the run summary.

## Local staging and dry runs

Use JDK 21. The `publication` profile stages to a local file repository. Run
these commands from the revision you intend to publish.

```sh
./mvnw --batch-mode --no-transfer-progress -Ppublication clean deploy
```

Supply the signing variables above and the token through your environment or
your private JReleaser settings file. As in Pembana Search, for local release
deployments set `JRELEASER_MAVENCENTRAL_SONATYPE_USERNAME` and
`JRELEASER_MAVENCENTRAL_SONATYPE_PASSWORD` to the same token values as the
`JRELEASER_NEXUS2_*` variables. Dry runs still require usable signing
configuration and can make read-only requests.

```sh
./mvnw --batch-mode --no-transfer-progress -N -Ppublication \
  -Djreleaser.dry.run=true jreleaser:deploy
```

Review `target/jreleaser/trace.log` and `target/jreleaser/output.properties`.
For artifact-only publication, rerun the JReleaser command without
`-Djreleaser.dry.run=true`. JReleaser can load
`~/.jreleaser/config.properties`; check it for stale credentials or key
exports before publishing.

### Prepare a full release locally

For the command-line equivalent of the workflow, start with a clean checkout
of current `main` and configure Git to sign with your registered OpenPGP key.
Use the same preparation script as CI. It creates both signed commits, the
signed tag, and rendered release notes locally.

```sh
base=$(git rev-parse HEAD)
tag=v1.0.0-M2
release_dir=$(mktemp -d)
bash .github/release/prepare.sh "${tag#v}" 1.0.0-SNAPSHOT \
  "$release_dir" https://github.com/pembana/pembana-image-filters &&
git switch --detach "$tag" &&
./mvnw --batch-mode --no-transfer-progress -Ppublication clean deploy &&
./mvnw --batch-mode --no-transfer-progress -N -Ppublication \
  -Djreleaser.dry.run=true jreleaser:deploy
```

After reviewing the dry run and `$release_dir/release-notes.md`, upload and
validate before pushing the Git refs. Export `CENTRAL_USERNAME` and
`CENTRAL_PASSWORD` with the same Central token for the validation helper.
Run the following as one block and stop if it fails.

```sh
(
  set -e
  JRELEASER_MAVENCENTRAL_STAGE=UPLOAD \
    ./mvnw --batch-mode --no-transfer-progress -N -Ppublication jreleaser:deploy
  deployment_id=$(sed -n 's/^deployMavenCentralSonatypeDeploymentId=//p' \
    target/jreleaser/output.properties)
  test "$(bash .github/release/central.sh "$deployment_id")" = VALIDATED
  bash .github/release/push.sh "$base" "$tag"
  JRELEASER_MAVENCENTRAL_STAGE=PUBLISH JRELEASER_MAVENCENTRAL_DEPLOYMENT_ID="$deployment_id" \
    ./mvnw --batch-mode --no-transfer-progress -N -Ppublication jreleaser:deploy
  test "$(bash .github/release/central.sh "$deployment_id" --wait)" = PUBLISHED
)
```

Once Central reports `PUBLISHED`, create the GitHub release with the rendered
notes. Use `--prerelease` for a milestone or RC. Finally, return to `main` and
delete the temporary local branch.

```sh
gh release create "$tag" --verify-tag --title "Pembana Image Filters ${tag#v}" \
  --notes-file "$release_dir/release-notes.md" --prerelease &&
git switch main &&
git branch -d release-next
```
