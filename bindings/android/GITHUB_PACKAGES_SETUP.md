# GitHub Packages Setup Guide

This guide explains how to set up GitHub Packages for the bitkit-core Android library to replace Jitpack.

## Prerequisites

You'll need:
- GitHub repository with admin access
- GitHub Personal Access Token with appropriate scopes

## Setup Steps

### 1. Create GitHub Personal Access Token

1. Go to GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token (classic)"
3. Set expiration and select scopes:
   - **For publishing**: `write:packages`, `read:packages`
   - **For consuming**: `read:packages`
4. Copy the generated token (you won't see it again!)

### 2. Configure Local Environment

#### Option A: Using github.properties (Recommended)
1. Create `bindings/android/github.properties` with your credentials:
```properties
gpr.usr=YOUR_GITHUB_USERNAME
gpr.key=YOUR_PERSONAL_ACCESS_TOKEN
```
2. The file is already added to `.gitignore` for security

#### Option B: Using Environment Variables
Set these environment variables:
```sh
export GPR_USER="YOUR_GITHUB_USERNAME"
export GPR_API_KEY="YOUR_PERSONAL_ACCESS_TOKEN"
```

### 3. Publish the Library

1. Navigate to the Android bindings directory:
```sh
cd bindings/android
```

2. Build and publish:
```sh
./gradlew clean build publish
```

3. Verify the package appears in your GitHub repository under the "Packages" tab

### 4. Configure Package Visibility (GitHub UI)

1. Go to your repository on GitHub
2. Click on "Packages" tab
3. Click on the published package
4. Go to "Package settings"
5. Configure visibility:
   - **Public**: Anyone can download
   - **Private**: Only you and collaborators

### 5. Update Repository Settings (Optional)

For public packages, consider:
1. Repository → Settings → Actions → General
2. Set "Workflow permissions" to allow GitHub Actions to publish packages

## Troubleshooting

### Verification Commands

```bash
# Test authentication
./gradlew dependencies --configuration implementation

# Test publishing (dry run)
./gradlew publishToMavenLocal

# Actual publish
./gradlew publish
```
