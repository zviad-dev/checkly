package ru.hh.checkly.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.checkly.dao.EventDao;
import ru.hh.checkly.dao.RecommendationDao;
import ru.hh.checkly.dao.RecruiterDao;
import ru.hh.checkly.dto.EventDto;
import ru.hh.checkly.entity.*;
import ru.hh.checkly.exception.RestErrorResponseCode;
import ru.hh.checkly.exception.RestException;
import ru.hh.checkly.service.mapping.EventMapper;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

  private final EventDao eventDao;
  private final NotificationService notificationService;
  private final RecruiterDao recruiterDao; // todo {strelchm} - move to person dao
  private final RecommendationDao recommendationDao;

  @Inject
  public EventService(EventDao eventDao, NotificationService notificationService, RecruiterDao recruiterDao, RecommendationDao recommendationDao) {
    this.eventDao = eventDao;
    this.notificationService = notificationService;
    this.recruiterDao = recruiterDao;
    this.recommendationDao = recommendationDao;
  }

  @Transactional(readOnly = true)
  public List<EventDto> getAll(Long personId) {
    return eventDao.getAllByPersonId(personId)
        .stream()
        .map(EventMapper::toEventDto)
        .collect(Collectors.toList());
  }

  @Transactional
  public void create(String message, Person person, Recommendation recommendation) {
    Event event = getEvent(message, person);
    event.setRecommendation(recommendation);
    eventDao.save(event);

    notificationService.sendToPerson(person.getId(), message);
  }

  @Transactional
  public void createForSurvey(String message, Person person, Survey survey) {
    Event event = getEvent(message, person);
    event.setSurveyId(survey.getSurveyId());
    eventDao.save(event);

    notificationService.sendToPerson(person.getId(), message);
  }

  @Transactional
  public void deleteAllByPersonId(Long personId) {
    eventDao.deleteAllByPersonId(personId);
  }

  @Transactional
  public void viewEvents(Long personId, Collection<UUID> eventIds) {
    if (eventIds == null) {
      eventDao.setViewed(personId);
    } else {
      eventDao.setViewed(personId, eventIds);
    }
  }

  @NotNull
  private Event getEvent(String message, Person person) {
    Event event = new Event();
    event.setMessage(message);
    event.setCreated(LocalDateTime.now());
    event.setPerson(person);
    return event;
  }
}
