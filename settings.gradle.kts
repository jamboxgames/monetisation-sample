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

        //Repository for the monetisation-sdk
        maven { url = uri("https://www.jitpack.io" ) }

        //Repositories for the applovin mediation adapters
        maven { url = uri("https://cboost.jfrog.io/artifactory/chartboost-ads/" ) }
        maven { url = uri("https://android-sdk.is.com" ) }
        maven { url = uri("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea" ) }
        maven { url = uri("https://artifact.bytedance.com/repository/pangle" ) }
        maven { url = uri("https://s3.amazonaws.com/smaato-sdk-releases/" ) }
        maven { url = uri("https://verve.jfrog.io/artifactory/verve-gradle-release" ) }
    }
}

rootProject.name = "MonetisationDemo"
include(":app")
 