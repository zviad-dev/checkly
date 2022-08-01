package ru.hh.checkly;

import ru.hh.checkly.config.ProdConfig;
import ru.hh.checkly.exception.mapper.InternalErrorExceptionMapper;
import ru.hh.checkly.exception.mapper.RestExceptionMapper;
import ru.hh.checkly.service.security.AuthenticationFilter;
import ru.hh.nab.starter.NabApplication;
import ru.hh.nab.websocket.NabWebsocketConfigurator;

import java.util.Set;

public class App {

  public static void main(String[] args) {
    NabApplication
            .builder()
            .configureJersey()
            .registerResources(AuthenticationFilter.class, RestExceptionMapper.class, InternalErrorExceptionMapper.class)
            .bindToRoot()
            .apply(builder -> NabWebsocketConfigurator.configureWebsocket(builder, Set.of("ru.hh.checkly.resource")))
            .build()
            .run(ProdConfig.class);
  }
}
