package com.snowman.project.service.awards.event

import com.snowman.project.model.awards.entity.Award
import com.snowman.project.model.common.entity.DomainEvent

class AwardSaveEvent(source: Award, message: String = "Award saved") : DomainEvent(source, message) {
}