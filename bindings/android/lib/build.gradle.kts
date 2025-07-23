import java.io.FileInputStream
import java.util.Properties

// Load github.properties
val githubProperties = Properties()
val githubPropertiesFile = rootProject.file("github.properties")
if (githubPropertiesFile.exists()) {
    githubProperties.load(FileInputStream(githubPropertiesFile))
}

// library version is defined in gradle.properties
val libraryVersion: String by project

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android") version "1.9.10"

    id("maven-publish")
    id("signing")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
}

repositories {
    mavenCentral()
    google()
}

android {
    namespace = "com.synonym.bitkitcore"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(file("proguard-android-optimize.txt"), file("proguard-rules.pro"))
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation("net.java.dev.jna:jna:5.17.0@aar")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.12.0")
    api("org.slf4j:slf4j-api:1.7.36")

    androidTestImplementation("com.github.tony19:logback-android:2.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    androidTestImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/ovitrif/bitkit-core")
                credentials {
                    username = githubProperties.getProperty("gpr.user") as String? ?: project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                    password = githubProperties.getProperty("gpr.key") as String? ?: project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
        publications {
            register<MavenPublication>("gpr") {
                groupId = "com.ovitrif"
                artifactId = "bitkit-core-android"
                version = libraryVersion

                from(components["release"])
                pom {
                    name.set("bitkit-core-android")
                    description.set(
                        "Bitkit Core Android bindings library."
                    )
                    url.set("https://github.com/ovitrif/bitkit-core")
                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://github.com/ovitrif/bitkit-core/blob/master/LICENSE")
                        }
                    }
                    developers {
                        developer {
                            id.set("synonymdev")
                            name.set("Synonym")
                            email.set("noreply@synonym.to")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/ovitrif/bitkit-core.git")
                        developerConnection.set("scm:git:ssh://github.com:ovitrif/bitkit-core.git")
                        url.set("https://github.com/ovitrif/bitkit-core/tree/master")
                    }
                }
            }
        }
    }
}

ktlint {
    filter {
        exclude { entry ->
            entry.file.toString().contains("main")
        }
    }
}
