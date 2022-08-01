package ru.hh.checkly.dao;

import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.Event;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventDao extends GenericDao {

  @Inject
  public EventDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public void deleteAllByPersonId(Long personId) {
    getSession().createQuery("DELETE FROM Event e WHERE e.person.id = :personId")
      .setParameter("personId", personId)
      .executeUpdate();
  }

  public List<Event> getAllByPersonId(Long personId) {
    return getSession().createQuery("FROM Event e "  +
        "WHERE e.person.id = :personId ORDER BY e.created DESC", Event.class)
        .setParameter("personId", personId)
        .stream()
        .collect(Collectors.toList());
  }

  public int setViewed(Long personId) {
    return getSession().createQuery("UPDATE Event e " +
            "SET viewed = TRUE " +
            "WHERE e.person.id = :personId AND e.viewed = FALSE")
        .setParameter("personId", personId)
        .executeUpdate();
  }

  public int setViewed(Long personId, Collection<UUID> eventIds) {
    return getSession().createQuery("UPDATE Event e " +
            "SET viewed = TRUE " +
            "WHERE e.id IN :eventIds AND e.viewed = FALSE AND e.person.id = :personId")
        .setParameter("personId", personId)
        .setParameter("eventIds", eventIds)
        .executeUpdate();
  }

  public UUID save(Event event) {
    getSession().saveOrUpdate(event);
    return event.getId();
  }
}
