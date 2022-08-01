package ru.hh.checkly.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import ru.hh.nab.common.properties.FileSettings;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Singleton
public class LiquibaseStarter {
  private final DataSource ds;
  private final FileSettings fileSettings;

  @Inject
  public LiquibaseStarter(DataSource ds, FileSettings fileSettings) {
    this.ds = ds;
    this.fileSettings = fileSettings;
  }

  @PostConstruct
  protected void actualize() throws LiquibaseException, SQLException {
    ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
    try (Connection connection = ds.getConnection()) {
      JdbcConnection jdbcConnection = new JdbcConnection(connection);
      Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

      Properties properties = fileSettings.getSubProperties("liquibase");
      Liquibase liquiBase = new Liquibase(properties.getProperty("changelog.path"), resourceAccessor, db);
      liquiBase.update(properties.getProperty("stage"));
    }
  }
}
