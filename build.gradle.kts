
plugins {
    alias(libs.plugins.android.application) apply false
    // Centralizamos la versión 2.2.21 aquí
    kotlin("android") version "2.2.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.21" apply false
}