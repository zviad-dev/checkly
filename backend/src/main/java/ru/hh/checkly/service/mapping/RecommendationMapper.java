package ru.hh.checkly.service.mapping;

import ru.hh.checkly.dto.RecommendationAnswersDto;
import ru.hh.checkly.dto.RecommendationDto;
import ru.hh.checkly.dto.SurveyBaseDto;
import ru.hh.checkly.dto.SurveyRecommendationDto;
import ru.hh.checkly.dto.SurveyRecommendationsDto;
import ru.hh.checkly.entity.Candidate;
import ru.hh.checkly.entity.Recommendation;
import ru.hh.checkly.entity.Recommender;
import ru.hh.checkly.entity.Survey;
import java.util.Set;
import java.util.stream.Collectors;
import static ru.hh.checkly.service.mapping.CandidateMapper.toCandidateDto;

public class RecommendationMapper {

  public static RecommendationDto toRecommendationDto(Recommendation recommendation) {
    return new RecommendationDto(recommendation.getRecommendationId(),
      recommendation.getRecommender().getRecommenderFirstName(),
      recommendation.getRecommender().getRecommenderLastName(),
      recommendation.getRecommender().getRecommenderEmail(),
      recommendation.getStatus(),
      recommendation.getAnswers().isEmpty() ?
        Set.of() :
        recommendation.getAnswers()
          .stream()
          .map(AnswerMapper::toAnswerDto)
          .collect(Collectors.toSet())
    );
  }

  public static SurveyBaseDto toSurveyDto(Survey survey) {
    return new SurveyRecommendationsDto(survey.getSurveyId(),
      toCandidateDto(survey.getCandidate()),
      survey.getStatus(),
      survey.getCreationTime(),
      survey.getUpdateTime(),
      survey.getRecommendations().isEmpty() ?
        Set.of() :
        survey.getRecommendations()
          .stream()
          .map(RecommendationMapper::toRecommendationDto)
          .collect(Collectors.toSet()),
      survey.getPositionName()
    );
  }

  public static SurveyBaseDto toRecommendationWithSurveyInfoDto(Recommendation recommendation) {
    Survey survey = recommendation.getSurvey();

    return new SurveyRecommendationDto(survey.getSurveyId(),
      toCandidateDto(survey.getCandidate()),
      survey.getStatus(),
      survey.getCreationTime(),
      survey.getUpdateTime(),
      RecommendationMapper.toRecommendationDto(recommendation),
      survey.getPositionName()
    );
  }

  public static RecommendationAnswersDto toRecommendationAnswersDto(Recommendation recommendation) {
    Recommender recommender = recommendation.getRecommender();
    Candidate candidate = recommendation.getSurvey().getCandidate();

    return new RecommendationAnswersDto(candidate.getCandidateFirstName(),
      candidate.getCandidateLastName(),
      recommender.getRecommenderFirstName(),
      recommender.getRecommenderLastName(),
      recommender.getRecommenderEmail(),
      recommendation.getAnswers().isEmpty() ?
        Set.of() :
        recommendation.getAnswers()
          .stream()
          .map(AnswerMapper::toAnswerDto)
          .collect(Collectors.toSet())
    );
  }
}
