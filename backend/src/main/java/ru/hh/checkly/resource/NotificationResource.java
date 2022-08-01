package ru.hh.checkly.resource;

import org.springframework.web.socket.server.standard.SpringConfigurator;
import ru.hh.checkly.service.NotificationService;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/events/{recruiterId}/new_event_notification", configurator = SpringConfigurator.class)
public class NotificationResource {

  @Inject
  private NotificationService eventService;

  @OnOpen
  public void onOpen(Session session, @PathParam("recruiterId") Long recruiterId) throws IOException {
    eventService.onOpen(recruiterId, session);
  }

  @OnClose
  public void onClose(Session session, @PathParam("recruiterId") Long recruiterId) throws IOException {
    eventService.onClose(recruiterId, session);
  }
}
