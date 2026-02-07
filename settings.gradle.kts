pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.google.gms.google-services") {
                // pick the plugin artifact coordinate and version you want to use:
                useModule("com.google.gms:google-services:4.4.0")
            }
            if (requested.id.id == "com.google.firebase.crashlytics") {
                // pick the plugin artifact coordinate and version you want to use:
                useModule("com.google.firebase:firebase-crashlytics-gradle:3.0.6")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "github-api-playground"
include(":app")
include(":domain")
include(":data")
include(":core")
