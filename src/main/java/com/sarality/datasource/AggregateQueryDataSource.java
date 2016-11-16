package com.sarality.datasource;

import com.sarality.db.SQLiteTable;
import com.sarality.db.TableRegistry;
import com.sarality.db.cursor.CursorDataExtractor;
import com.sarality.db.cursor.JoinQueryCursorDataExtractor;
import com.sarality.db.query.SimpleJoinQueryBuilder;
import com.sarality.db.query.SimpleQueryBuilder;

import java.util.List;

/**
 * Reads data from a table using an aggregate query and a cursor data extractor
 *
 * @author satya@ (Satya)
 */
public class AggregateQueryDataSource<K, T> implements DataSource<List<K>> {

  // TODO(abhideep): Use table rather than SQLite inheritance <-- kept this todo as a copy from TableDataSource (satya)
  private final SQLiteTable<T> table;
  private final SimpleQueryBuilder queryBuilder;
  private final CursorDataExtractor<K> cursorDataExtractor;

  private List<K> dataList;

  public AggregateQueryDataSource(TableRegistry registry, String tableName, SimpleQueryBuilder queryBuilder,
      CursorDataExtractor<K> cursorDataExtractor) {
    this.table = (SQLiteTable<T>) registry.getTable(tableName);
    this.queryBuilder = queryBuilder;
    this.cursorDataExtractor = cursorDataExtractor;
  }

  @Override
  public List<K> load() {
    try {
      table.open();
      dataList = table.readAll(queryBuilder.build(), cursorDataExtractor);
      return dataList;
    } finally {
      table.close();
    }
  }

  @Override
  public List<K> getData() {
    return dataList;
  }
}
