package ru.hh.checkly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.checkly.client.VkHttpClient;
import ru.hh.checkly.dao.AnswerDao;
import ru.hh.checkly.dao.CandidateDao;
import ru.hh.checkly.dao.EventDao;
import ru.hh.checkly.dao.PersonDao;
import ru.hh.checkly.dao.RecommendationDao;
import ru.hh.checkly.dao.RecommenderDao;
import ru.hh.checkly.dao.RecruiterDao;
import ru.hh.checkly.dao.StatisticsDao;
import ru.hh.checkly.dao.SurveyDao;
import ru.hh.checkly.dao.SurveyQuestionDao;
import ru.hh.checkly.dao.TemplateDao;
import ru.hh.checkly.dao.TemplateQuestionDao;
import ru.hh.checkly.entity.Answer;
import ru.hh.checkly.entity.Candidate;
import ru.hh.checkly.entity.Event;
import ru.hh.checkly.entity.Person;
import ru.hh.checkly.entity.Recommendation;
import ru.hh.checkly.entity.Recommender;
import ru.hh.checkly.entity.Recruiter;
import ru.hh.checkly.entity.Survey;
import ru.hh.checkly.entity.SurveyQuestion;
import ru.hh.checkly.entity.Template;
import ru.hh.checkly.entity.TemplateQuestion;
import ru.hh.checkly.resource.AuthenticationResource;
import ru.hh.checkly.resource.EventResource;
import ru.hh.checkly.resource.NotificationResource;
import ru.hh.checkly.resource.RecommendationAnswersResource;
import ru.hh.checkly.resource.RecommendationResource;
import ru.hh.checkly.resource.RegistrationResource;
import ru.hh.checkly.resource.SettingsResource;
import ru.hh.checkly.resource.StatisticsResource;
import ru.hh.checkly.resource.SurveyResource;
import ru.hh.checkly.resource.TemplateResource;
import ru.hh.checkly.resource.VkRedirectResource;
import ru.hh.checkly.service.EventService;
import ru.hh.checkly.service.NotificationService;
import ru.hh.checkly.service.RecommendationAnswersService;
import ru.hh.checkly.service.RecommendationService;
import ru.hh.checkly.service.RegistrationService;
import ru.hh.checkly.service.SettingsService;
import ru.hh.checkly.service.StatisticsService;
import ru.hh.checkly.service.SurveyService;
import ru.hh.checkly.service.TemplateService;
import ru.hh.checkly.service.VkAuthService;
import ru.hh.checkly.service.security.AuthenticationService;
import ru.hh.checkly.service.security.SecurityContextProvider;
import ru.hh.nab.hibernate.MappingConfig;
import ru.hh.nab.starter.NabCommonConfig;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
@Import({
  NabCommonConfig.class,
  MailConfig.class,
  LiquibaseStarter.class,
  VkHttpClient.class,
  PersonDao.class,
  RecruiterDao.class,
  SurveyDao.class,
  CandidateDao.class,
  SurveyQuestionDao.class,
  TemplateQuestionDao.class,
  RegistrationResource.class,
  AuthenticationResource.class,
  VkRedirectResource.class,
  SettingsResource.class,
  SurveyResource.class,
  RegistrationService.class,
  AuthenticationService.class,
  TemplateService.class,
  TemplateResource.class,
  TemplateDao.class,
  AnswerDao.class,
  TemplateQuestionDao.class,
  LiquibaseStarter.class,
  VkRedirectResource.class,
  VkHttpClient.class,
  VkAuthService.class,
  SettingsResource.class,
  SettingsService.class,
  StatisticsService.class,
  StatisticsResource.class,
  StatisticsDao.class,
  SettingsService.class,
  SurveyResource.class,
  SurveyService.class,
  SettingsService.class,
  RecommendationService.class,
  RecommendationDao.class,
  RecommendationResource.class,
  SurveyDao.class,
  RecommenderDao.class,
  RecommendationAnswersResource.class,
  RecommendationAnswersService.class,
  SurveyQuestionDao.class,
  AnswerDao.class,
  SecurityContextProvider.class,
  NotificationService.class,
  NotificationResource.class,
  EventResource.class,
  EventService.class,
  EventDao.class,
})
public class CommonConfig {

  @Bean
  public MappingConfig mappingConfig() {
    MappingConfig mappingConfig = new MappingConfig();
    mappingConfig.addEntityClass(Answer.class);
    mappingConfig.addEntityClass(Candidate.class);
    mappingConfig.addEntityClass(Person.class);
    mappingConfig.addEntityClass(Recommendation.class);
    mappingConfig.addEntityClass(Recommender.class);
    mappingConfig.addEntityClass(Recruiter.class);
    mappingConfig.addEntityClass(Survey.class);
    mappingConfig.addEntityClass(SurveyQuestion.class);
    mappingConfig.addEntityClass(Template.class);
    mappingConfig.addEntityClass(TemplateQuestion.class);
    mappingConfig.addEntityClass(Event.class);
    return mappingConfig;
  }

  @Bean
  public Client client() {
    return ClientBuilder.newClient();
  }

}
