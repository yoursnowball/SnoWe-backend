package com.snowman.project.config.configurations

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.PathResource
import java.io.File
import java.io.IOException
import javax.annotation.PostConstruct

@Configuration
class FcmConfig {

    @PostConstruct
    @Throws(IOException::class)
    fun initFcm() {
        val serviceAccount = PathResource(".${File.separator}Snowe-bakend-private-file${File.separator}firebase${File.separator}snowe-firebase-adminsdk.json").inputStream

        val options: FirebaseOptions = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        FirebaseApp.initializeApp(options)
    }
}
