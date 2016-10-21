package com.sarality.datasource;

import com.sarality.db.SQLiteTable;
import com.sarality.db.TableRegistry;
import com.sarality.db.cursor.JoinQueryCursorDataExtractor;
import com.sarality.db.query.SimpleJoinQueryBuilder;

import java.util.List;

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

  private List<T> dataList;

  public JoinQueryDataSource(TableRegistry registry, String tableName, SimpleJoinQueryBuilder queryBuilder,
      JoinQueryCursorDataExtractor<T> cursorDataExtractor) {
    this.table = (SQLiteTable<T>) registry.getTable(tableName);
    this.queryBuilder = queryBuilder;
    this.cursorDataExtractor = cursorDataExtractor;
  }

  @Override
  public List<T> load() {
    try {
      table.open();
      dataList = table.readAll(queryBuilder.build(), cursorDataExtractor);
      return dataList;
    } finally {
      table.close();
    }
  }

  @Override
  public List<T> getData() {
    return dataList;
  }
}
