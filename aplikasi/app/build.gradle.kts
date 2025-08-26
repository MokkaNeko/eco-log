import java.io.FileInputStream
import java.util.Properties

fun getLocalProperty(key: String): String {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(FileInputStream(localPropertiesFile))
    }
    return properties.getProperty(key) ?: throw GradleException("Property $key not found in local.properties")
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.mokaneko.recycle2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mokaneko.recycle2"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
       debug {
            buildConfigField("String", "CLOUDINARY_API_KEY", "\"${getLocalProperty("CLOUDINARY_API_KEY")}\"")
            buildConfigField("String", "CLOUDINARY_API_SECRET", "\"${getLocalProperty("CLOUDINARY_API_SECRET")}\"")
            buildConfigField("String", "CLOUDINARY_CLOUD_NAME", "\"${getLocalProperty("CLOUDINARY_CLOUD_NAME")}\"")
        }
        release {
            buildConfigField("String", "CLOUDINARY_API_KEY", "\"${getLocalProperty("CLOUDINARY_API_KEY")}\"")
            buildConfigField("String", "CLOUDINARY_API_SECRET", "\"${getLocalProperty("CLOUDINARY_API_SECRET")}\"")
            buildConfigField("String", "CLOUDINARY_CLOUD_NAME", "\"${getLocalProperty("CLOUDINARY_CLOUD_NAME")}\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        mlModelBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.firebase.firestore)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.core.splashscreen)


//    lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v262)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    
//    hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

//    firebase
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

//    cloudinary
    implementation(libs.cloudinary.android)

//    camera x
    implementation (libs.androidx.camera.core)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.view)
    implementation (libs.androidx.camera.extensions)
    implementation (libs.guava)

//    glide
    implementation (libs.glide)
    kapt (libs.compiler)
}