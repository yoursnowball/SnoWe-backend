package com.snowman.project.config.configurations

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter.ofPattern

@Configuration
open class DateTimeConfiguration : Jackson2ObjectMapperBuilderCustomizer {
    private val dateTimeFormat: String = "yyyy-MM-dd HH:mm:ss"
    private val timeFormat: String = "HH:mm"

    override fun customize(jacksonObjectMapperBuilder: Jackson2ObjectMapperBuilder) {
        jacksonObjectMapperBuilder
                .serializers(
                        LocalDateTimeSerializer(ofPattern(dateTimeFormat)),
                        LocalTimeSerializer(ofPattern(timeFormat))
                )
                .deserializers(
                        LocalDateTimeDeserializer(ofPattern(dateTimeFormat)),
                        LocalTimeDeserializer(ofPattern(timeFormat))
                )
    }
}