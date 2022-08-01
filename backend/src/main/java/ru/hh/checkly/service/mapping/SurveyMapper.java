package ru.hh.checkly.service.mapping;

import java.util.stream.Collectors;
import ru.hh.checkly.dto.CandidateDto;
import ru.hh.checkly.dto.RecommenderDto;
import ru.hh.checkly.dto.SurveyDto;
import ru.hh.checkly.dto.SurveyQuestionDto;
import ru.hh.checkly.entity.Survey;

public class SurveyMapper {
  private SurveyMapper() {
    throw new IllegalStateException();
  }

  public static SurveyDto toSurveyDto(Survey survey) {
    return new SurveyDto(survey.getSurveyId(),
        new CandidateDto(survey.getCandidate().getCandidateFirstName(),
            survey.getCandidate().getCandidateLastName(),
            survey.getCandidate().getCandidateEmail()),
        survey.getStatus(),
        survey.getCreationTime(),
        survey.getUpdateTime(),
        survey.getSurveyQuestions().stream()
            .map(surveyQuestion -> new SurveyQuestionDto(surveyQuestion.getQuestionId(), surveyQuestion.getQuestion()))
            .collect(Collectors.toSet()),
        survey.getRecommendations().stream()
            .map(surveyRecommendation -> new RecommenderDto(surveyRecommendation.getRecommender().getRecommenderId(),
                surveyRecommendation.getRecommender().getRecommenderFirstName(),
                surveyRecommendation.getRecommender().getRecommenderLastName(),
                surveyRecommendation.getRecommender().getRecommenderEmail()))
            .collect(Collectors.toSet()),
      survey.getPositionName());
  }

}
