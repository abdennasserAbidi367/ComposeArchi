import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    //alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.hilt).apply(false)
    alias(libs.plugins.dokka)
}
subprojects {
    plugins.matching { anyPlugin -> supportedPlugins(anyPlugin) }.whenPluginAdded {
        when (this) {
            is com.android.build.gradle.AppPlugin -> {
                configure<com.android.build.gradle.BaseExtension> {
                    val appId = libs.versions.app.version.appId.get()
                    namespace = appId
                    defaultConfig {
                        applicationId = appId
                    }
                }
            }

            is com.android.build.gradle.LibraryPlugin -> {
                configure<com.android.build.gradle.BaseExtension> {
                    namespace = libs.versions.app.version.groupId.get()
                        .plus(path.replace(":", ".").replace("-", "."))
                }
            }
        }
        configure<com.android.build.gradle.BaseExtension> {
            setCompileSdkVersion(project.libs.versions.app.build.compileSDKVersion.get().toInt())
            defaultConfig {
                minSdk = project.libs.versions.app.build.minimumSDK.get().toInt()
                targetSdk = project.libs.versions.app.build.targetSDK.get().toInt()
                versionName = project.libs.versions.app.version.versionName.get()
                versionCode = project.libs.versions.app.version.versionCode.get().toInt()
                testInstrumentationRunner = project.libs.versions.app.build.testRunner.get()

                vectorDrawables {
                    vectorDrawables.useSupportLibrary = true
                }
            }

            this@subprojects.addCompose(this)

            dependencies {
                add("androidTestImplementation", libs.bundles.androidTest)
                add("testImplementation", libs.bundles.unitTest)
                add("testRuntimeOnly", libs.testImplementation.runtime.jupiter.engine)
                add("debugImplementation", libs.testImplementation.compose.manifest)
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
                kotlinOptions.jvmTarget = project.libs.versions.app.build.kotlinJVMTarget.get()
                kotlinOptions.freeCompilerArgs = listOf(
                    "-Xcontext-receivers",
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                    "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                    "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                    "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                    "-opt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",
                    "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
                )
            }

            plugins.withType<com.android.build.gradle.LibraryPlugin> {
                configure<com.android.build.gradle.LibraryExtension> {

                    apply(plugin = libs.versions.gradlePlugins.maven.publish.get())
                    apply(plugin = libs.plugins.dokka.get().pluginId)

                    defaultConfig {
                        consumerProguardFiles("consumer-rules.pro")
                    }

                    buildTypes {
                        getByName("release") {
                            isMinifyEnabled = false
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                            )
                        }
                    }
                    /*afterEvaluate {
                        publishing.publications {
                            create<MavenPublication>("release") {
                                groupId = libs.versions.app.version.groupId.get()
                                artifactId = this@subprojects.name
                                version = libs.versions.app.version.versionName.get()
                                afterEvaluate {
                                    from(components["release"])
                                }
                            }
                        }
                    }*/
                }
            }

            tasks.withType<org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask>()
                .configureEach {
                    kaptProcessJvmArgs.add("-Xmx512m")
                }

            plugins.withType<org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin>() {
                configure<org.jetbrains.kotlin.gradle.plugin.KaptExtension>() {
                    useBuildCache = true
                    correctErrorTypes = true
                }
            }

            testOptions.unitTests.all {
                it.apply {
                    useJUnitPlatform()
                    testLogging {
                        events("passed", "failed", "skipped", "standardOut", "standardError")
                    }
                }
            }

            packagingOptions {
                resources {
                    excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1")
                }
            }

            buildFeatures.apply {
                aidl = false
                renderScript = false
                resValues = false
                shaders = false
                buildConfig = false
            }

            tasks.register("testAll") {
                dependsOn("test", "connectedAndroidTest")
                group = "custom_tasks"
                description = "Run all tests"
            }
        }
    }
}

fun Project.addCompose(baseExtension: com.android.build.gradle.BaseExtension) {
    baseExtension.buildFeatures.compose = true

    baseExtension.composeOptions {
        kotlinCompilerExtensionVersion = project.project.libs.versions.composeCompiler.get()
    }

    project.dependencies {
        add("implementation", libs.compose.runtime)
    }
}

fun supportedPlugins(anyPlugin: Plugin<*>?) =
    anyPlugin is com.android.build.gradle.AppPlugin || anyPlugin is com.android.build.gradle.LibraryPlugin