buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.5.2")
    }
}

group = providers.gradleProperty("group").orNull ?: "com.synonym"
version = providers.gradleProperty("version").orNull ?: "0.0.0"
