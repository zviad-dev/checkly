package ru.hh.checkly.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@EnableAsync
public class NotificationService {
  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);
  public static final int INITIAL_DELAY = 10;
  public static final int TIMEOUT = 15;

  private Map<Long, Set<Session>> recruiterSessions;
  private ScheduledExecutorService scheduledExecutorService;

  @PostConstruct
  public void postConstruct() {
    recruiterSessions = new ConcurrentHashMap<>();
    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    scheduledExecutorService.scheduleAtFixedRate(() -> // todo {strelchm} - session TTL clear
        LOGGER.debug("has {} websocket sessions active", recruiterSessions.values().stream().mapToLong(Set::size).sum()),
        INITIAL_DELAY,
        TIMEOUT,
        TimeUnit.SECONDS
    );
  }


  @Async
  public void sendToPerson(Long personId, String message) {
    recruiterSessions.getOrDefault(personId, new HashSet<>()).forEach(session -> {
      try {
        session.getBasicRemote().sendObject(message);
      } catch (IOException | EncodeException e) {
        LOGGER.debug("Error during event sending", e);
      }
    });
  }

  public void onOpen(Long recruiterId, Session session) throws IOException {
    Set<Session> sessions = recruiterSessions.getOrDefault(recruiterId, new HashSet<>());
    sessions.add(session);
    recruiterSessions.put(recruiterId, sessions);
  }

  public void onClose(Long recruiterId, Session session) throws IOException {
    Set<Session> sessions = recruiterSessions.get(recruiterId);
    if (sessions != null) {
      sessions.remove(session);
    }
    // WebSocket connection closes
  }
}
