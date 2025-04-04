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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "loop"
include(":app")
include(":domain")
include(":domain:model")
include(":domain:usecase")
include(":data")
include(":data:model")
include(":data:source-local")
include(":data:repository")
include(":core")
include(":core:common")
include(":core:navigation")
include(":core:ui")
include(":feature")
include(":feature:splash")
include(":feature:feed")
include(":feature:camera")
include(":feature:profile")
include(":feature:post")
include(":feature:discover")
include(":feature:notification")
