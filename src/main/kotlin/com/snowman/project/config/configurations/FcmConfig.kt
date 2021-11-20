package com.snowman.project.config.configurations

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import javax.annotation.PostConstruct

@Configuration
open class FcmConfig {

    @PostConstruct
    @Throws(IOException::class)
    open fun initFcm() {
        val serviceAccount = ClassPathResource("firebase/firebase-adminSdk-key.json").inputStream
        var firebaseApp: FirebaseApp? = null
        val firebaseApps = FirebaseApp.getApps()

        if (firebaseApps != null && firebaseApps.isNotEmpty()) {
            for (app: FirebaseApp in firebaseApps) {
                if (app.name == FirebaseApp.DEFAULT_APP_NAME) {
                    firebaseApp = app
                }
            }
        } else {
            val options: FirebaseOptions = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://ground-1b3d3.firebaseio.com")
                .build()
            FirebaseApp.initializeApp(options)
        }
    }
}