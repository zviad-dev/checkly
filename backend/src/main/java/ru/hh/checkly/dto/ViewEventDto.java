package ru.hh.checkly.dto;

import java.util.List;
import java.util.UUID;

public class ViewEventDto {
  private List<UUID> ids;

  public List<UUID> getIds() {
    return ids;
  }

  public void setIds(List<UUID> ids) {
    this.ids = ids;
  }
}
