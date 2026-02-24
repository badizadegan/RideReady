// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // KSP Plugin wird global definiert,
    // aber nur in Modulen aktiviert, die es brauchen
    alias(libs.plugins.ksp) apply false
}