package ru.hh.checkly.service.security;

public class SecurityContext {

  private long id;

  public SecurityContext() {
  }

  public SecurityContext(Long id) {
    this.id = id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

}
