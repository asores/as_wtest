# Aplicativo teste AS-WTest

Para este aplicativo foi utilizado a tecnologia Kotlin com arquitetura MVP.
Abaixo deixo as bibliotecas principais que foi utilizada:

    // ROOM components
    implementation "androidx.room:room-ktx:$rootProject.roomVersion"
    kapt "androidx.room:room-compiler:$rootProject.roomVersion"
    androidTestImplementation "androidx.room:room-testing:$rootProject.roomVersion"

    // Lifecycle components
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$rootProject.lifecycleVersion"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$rootProject.lifecycleVersion"
    implementation "androidx.lifecycle:lifecycle-common-java8:$rootProject.lifecycleVersion"

    // Kotlin components
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-core:$rootProject.coroutines"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:$rootProject.coroutines"

    // UI
    implementation "androidx.constraintlayout:constraintlayout:$rootProject.constraintLayoutVersion"
    implementation "com.google.android.material:material:$rootProject.materialVersion"
    
    // LEITURA DO ARQUIVO EXCEL
    implementation 'com.opencsv:opencsv:5.4'

Os gradles estão configurado para a última versão do Android Studio. 

# Por questões de perfomance e memória, implementei o scollInfinte com RecycleView, o mesmo está a preencher de 100 a 100.

*Se acharem necessário, deixei uma .apk de debug na pasta APK*

Teste do Alex Soares - AWTest
