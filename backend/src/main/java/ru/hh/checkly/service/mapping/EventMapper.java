package ru.hh.checkly.service.mapping;

import ru.hh.checkly.dto.EventDto;
import ru.hh.checkly.entity.Event;

public class EventMapper {
  private EventMapper() {
    throw new IllegalStateException();
  }

  public static EventDto toEventDto(Event event) {
    EventDto dto = new EventDto(event.getId(), event.getMessage(), event.getCreated(), event.isViewed(), event.getRecommendation());
    if (event.getSurveyId() != null) {
      dto.setSurveyId(event.getSurveyId());
    }
    return dto;
  }
}
