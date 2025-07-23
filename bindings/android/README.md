# Bitkit Core Android

Android library for Bitkit Core bindings.

## Installation

### Via JitPack (Recommended)

See [Jitpack.io](https://jitpack.io/).

### Via Maven Local (Development)

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        mavenLocal()
        // ... other repositories
    }
}

// build.gradle.kts
dependencies {
    implementation("com.synonym:bitkit-core-android:LATEST_VERSION")
}
```

### Via GitHub Packages (Recommended)

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/synonymdev/bitkit-core")
            credentials {
                username = System.getenv("GPR_USER")
                password = System.getenv("GPR_API_KEY")
            }
        }
    }
}

// build.gradle.kts
dependencies {
    implementation("com.synonym:bitkit-core-android:LATEST_VERSION")
}
```

### Github Packages Auth Setup

1. Create a Personal Access Token in GitHub:
   - Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
   - Generate new token with `read:packages` scope

2. Set environment variables:
- `GPR_USER`: Your GitHub username
- `GPR_API_KEY`: Your Personal Access Token

## Publishing (for maintainers)

To publish a new version to GitHub Packages:

```sh
cd bindings/android
./gradlew publish
```

Make sure you have proper GitHub credentials configured in environment variables with a token having `write:packages` scope.
