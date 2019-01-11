package com.sarality.datasource;

import android.content.Context;

import com.sarality.db.SQLiteTable;
import com.sarality.db.TableRegistry;
import com.sarality.db.cursor.JoinQueryCursorDataExtractor;
import com.sarality.db.query.SimpleJoinQueryBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Reads data from a Table using a custom Data Extractor for a Raw Query
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class JoinQueryDataSource<T> implements DataSource<List<T>> {

  // TODO(abhideep): User table rather than SQLite inheritance
  private final Context context;
  private final SQLiteTable<T> table;
  private final SimpleJoinQueryBuilder queryBuilder;
  private final JoinQueryCursorDataExtractor<T> cursorDataExtractor;

  private List<T> dataList;

  public JoinQueryDataSource(TableRegistry registry, String tableName, SimpleJoinQueryBuilder queryBuilder,
      JoinQueryCursorDataExtractor<T> cursorDataExtractor) {
    this(null, registry, tableName, queryBuilder, cursorDataExtractor);
  }

  public JoinQueryDataSource(Context context, TableRegistry registry, String tableName,
      SimpleJoinQueryBuilder queryBuilder, JoinQueryCursorDataExtractor<T> cursorDataExtractor) {
    this.context = context;
    this.table = (SQLiteTable<T>) registry.getTable(tableName);
    this.queryBuilder = queryBuilder;
    this.cursorDataExtractor = cursorDataExtractor;
  }

  @Override
  public List<T> load() {
    try {
      table.open();
      if (context != null) {
        attachDatabases();
      }
      dataList = table.readAll(queryBuilder.build(), cursorDataExtractor);
      return dataList;
    } finally {
      if (context != null) {
        detachDatabases();
      }
      table.close();
    }
  }

  private void attachDatabases() {
    Set<String> dbNameSet = new HashSet<>();
    List<String> dbNameList = queryBuilder.getDatabaseList();
    if (dbNameList != null && !dbNameList.isEmpty()) {
      for (String dbName : dbNameList) {
        if (!dbNameSet.contains(dbName)) {
          String sql = "ATTACH DATABASE ? AS " + queryBuilder.getDatabaseAlias(dbName);
          String[] queryArgs = new String[] {context.getDatabasePath(dbName).getPath()};
          table.execSQL(sql, queryArgs);
          dbNameSet.add(dbName);
        }
      }
    }
  }

  private void detachDatabases() {
    Set<String> dbNameSet = new HashSet<>();
    List<String> dbNameList = queryBuilder.getDatabaseList();
    if (dbNameList != null && !dbNameList.isEmpty()) {
      for (String dbName : dbNameList) {
        if (!dbNameSet.contains(dbName)) {
          String sql = "DETACH DATABASE " + queryBuilder.getDatabaseAlias(dbName);
          table.execSQL(sql, null);
          dbNameSet.add(dbName);
        }
      }
    }
  }

  @Override
  public List<T> getData() {
    return dataList;
  }
}
