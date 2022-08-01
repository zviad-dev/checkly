package ru.hh.checkly.service.mapping;

import ru.hh.checkly.dto.RecommenderDto;
import ru.hh.checkly.entity.Recommender;

public class RecommenderMapper {

  public static Recommender toRecommender(RecommenderDto recommenderDto) {
    return new Recommender(recommenderDto.getFirstName(),
      recommenderDto.getLastName(),
      recommenderDto.getEmail());
  }
}
