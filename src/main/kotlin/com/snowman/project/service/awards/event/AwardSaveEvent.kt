package com.snowman.project.service.awards.event

import com.snowman.project.model.awards.entity.Award
import com.snowman.project.service.common.event.DomainEvent

class AwardSaveEvent(source: Award, message: String = "Award saved") : DomainEvent(source, message) {
}