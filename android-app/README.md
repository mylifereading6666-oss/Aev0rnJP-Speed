# Aev0rnJP SPEED Android app

This is a lightweight Android WebView wrapper for the published Aev0rnJP SPEED site.

- App name: Aev0rnJP SPEED
- Package: `jp.aev0rnjp.speed`
- Minimum Android: 6.0 (API 23)
- Website: https://mylifereading6666-oss.github.io/Aev0rnJP-Speed/

## Build APK automatically

A GitHub Actions workflow at `.github/workflows/build-apk.yml` builds a debug APK when changes are pushed to `main`, or when manually run. Open the repository's **Actions** tab, select **Build Android APK**, open the newest successful run, and download the `Aev0rnJP-SPEED-debug-apk` artifact. Extract the ZIP to find the APK.

The app loads the published website, so internet access is required and speed-test traffic is measured by the website's existing endpoints. This workflow produces a debug APK for personal installation, not a Play Store release.
