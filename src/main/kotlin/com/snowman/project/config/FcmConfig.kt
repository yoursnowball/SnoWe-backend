package com.snowman.project.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.io.IOException
import javax.annotation.PostConstruct

@Configuration
class FcmConfig {

    @PostConstruct
    @Throws(IOException::class)
    fun initFcm() {
        val serviceAccount = ClassPathResource("firebase${File.separator}snowe-firebase-adminsdk.json").inputStream

        val options: FirebaseOptions = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        FirebaseApp.initializeApp(options)
    }
}
