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
            useModule("com.google.gms:google-services:4.4.4")
        }
        if (requested.id.id == "com.google.firebase.crashlytics") {
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
