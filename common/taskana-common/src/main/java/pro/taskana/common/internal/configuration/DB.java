package pro.taskana.common.internal.configuration;

import java.sql.Connection;
import java.sql.SQLException;

import pro.taskana.common.api.exceptions.SystemException;
import pro.taskana.common.api.exceptions.UnsupportedDatabaseException;

/** Supported versions of databases. */
public enum DB {
  H2("H2", "h2"),
  DB2("DB2", "db2"),
  ORACLE("Oracle", "oracle"),
  POSTGRES("PostgreSQL", "postgres");

  public final String dbProductName;
  public final String dbProductId;

  DB(String dbProductName, String dbProductId) {
    this.dbProductName = dbProductName;
    this.dbProductId = dbProductId;
  }

  public static boolean isH2(String dbProductId) {
    return H2.dbProductId.equals(dbProductId);
  }

  public static boolean isDb2(String dbProductId) {
    return DB2.dbProductId.equals(dbProductId);
  }

  public static boolean isPostgres(String dbProductId) {
    return POSTGRES.dbProductId.equals(dbProductId);
  }

  public static boolean isOracle(String dbProductId) {
    return ORACLE.dbProductId.equals(dbProductId);
  }

  public static boolean isOracleDb(String dbProductName) {
    return ORACLE.dbProductName.equals(dbProductName);
  }

  public static String getDatabaseProductName(Connection connection) throws SQLException {
    return connection.getMetaData().getDatabaseProductName();
  }

  public static DB getDbForId(String databaseId) {
    if (isH2(databaseId)) {
      return H2;
    } else if (isDb2(databaseId)) {
      return DB2;
    } else if (isOracle(databaseId)) {
      return ORACLE;
    } else if (isPostgres(databaseId)) {
      return POSTGRES;
    }
    throw new SystemException("Unknown database id: " + databaseId);
  }

  public static String getDatabaseProductId(Connection connection) throws SQLException {
    String dbProductName = DB.getDatabaseProductName(connection);
    if (dbProductName.contains(H2.dbProductName)) {
      return H2.dbProductId;
    } else if (dbProductName.contains(DB2.dbProductName)) {
      return DB2.dbProductId;
    } else if (dbProductName.contains(ORACLE.dbProductName)) {
      return ORACLE.dbProductId;
    } else if (POSTGRES.dbProductName.equals(dbProductName)) {
      return POSTGRES.dbProductId;
    } else {
      throw new UnsupportedDatabaseException(dbProductName);
    }
  }

  public String getProductId() {
    return this.dbProductId;
  }
}
