plugins {
    id("com.android.application") version Versions.gradleVersion apply false
    id("com.android.library") version Versions.gradleVersion apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlinVersion apply false
    id("com.google.dagger.hilt.android") version Versions.daggerHiltVersion apply false
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}