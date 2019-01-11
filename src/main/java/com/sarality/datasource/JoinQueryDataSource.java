package com.sarality.datasource;

import android.text.TextUtils;

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
  private final SQLiteTable<T> table;
  private final SimpleJoinQueryBuilder queryBuilder;
  private final JoinQueryCursorDataExtractor<T> cursorDataExtractor;
  private final boolean isMultiDbJoin;

  private List<T> dataList;

  public JoinQueryDataSource(TableRegistry registry, String tableName, SimpleJoinQueryBuilder queryBuilder,
      JoinQueryCursorDataExtractor<T> cursorDataExtractor) {
    this(registry, tableName, queryBuilder, cursorDataExtractor, false);
  }

  public JoinQueryDataSource(TableRegistry registry, String tableName,
      SimpleJoinQueryBuilder queryBuilder, JoinQueryCursorDataExtractor<T> cursorDataExtractor,
      boolean isMultiDbJoin) {
    this.table = (SQLiteTable<T>) registry.getTable(tableName);
    this.queryBuilder = queryBuilder;
    this.cursorDataExtractor = cursorDataExtractor;
    this.isMultiDbJoin = isMultiDbJoin;
  }

  @Override
  public List<T> load() {
    try {
      table.open();
      if (isMultiDbJoin) {
        attachDatabases();
      }
      dataList = table.readAll(queryBuilder.build(), cursorDataExtractor);
      return dataList;
    } finally {
      if (isMultiDbJoin) {
        detachDatabases();
      }
      table.close();
    }
  }

  private void attachDatabases() {
    Set<String> dbAliasSet = new HashSet<>();
    List<String> tableNameList = queryBuilder.getTablesWithDbAlias();
    if (tableNameList != null && !tableNameList.isEmpty()) {
      for (String tableName : tableNameList) {
        String dbAlias = queryBuilder.getDatabaseAlias(tableName);
        if (TextUtils.isEmpty(dbAlias)) {
          throw new IllegalStateException("Database Alias not registered for Table " + tableName);
        }
        if (!dbAliasSet.contains(dbAlias)) {
          SQLiteTable<?> dbTable = (SQLiteTable<?>) TableRegistry.getInstance().getTable(tableName);
          table.attachDatabase(dbTable, dbAlias);
          dbAliasSet.add(dbAlias);
        }
      }
    }
  }

  private void detachDatabases() {
    Set<String> dbAliasSet = new HashSet<>();
    List<String> tableNameList = queryBuilder.getTablesWithDbAlias();
    if (tableNameList != null && !tableNameList.isEmpty()) {
      for (String tableName : tableNameList) {
        String dbAlias = queryBuilder.getDatabaseAlias(tableName);
        if (TextUtils.isEmpty(dbAlias)) {
          throw new IllegalStateException("Database Alias not registered for Table " + tableName);
        }
        if (!dbAliasSet.contains(dbAlias)) {
          table.detachDatabase(dbAlias);
          dbAliasSet.add(dbAlias);
        }
      }
    }
  }

  @Override
  public List<T> getData() {
    return dataList;
  }
}
