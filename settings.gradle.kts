pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // Si besoin pour d'autres libs
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // Si nécessaire
    }
}

rootProject.name = "SensorExplorer"
include(":app")
