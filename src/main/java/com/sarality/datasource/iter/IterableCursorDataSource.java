package com.sarality.datasource.iter;

import com.sarality.db.SQLiteTable;
import com.sarality.db.TableRegistry;
import com.sarality.db.cursor.CursorDataExtractor;

import java.util.List;

/**
 * A DataSource that queries a Table based on a QueryIterator and return a different data object than the
 * one returned by the Table.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class IterableCursorDataSource<T, K, P> implements IterableDataSource<K, P> {
  protected final SQLiteTable<T> table;
  private final CursorDataExtractor<K> cursorDataExtractor;
  private final QueryIterator<K, P> iterator;

  protected List<K> dataList;

  public IterableCursorDataSource(String tableName, CursorDataExtractor<K> cursorDataExtractor,
      QueryIterator<K, P> iterator) {
    this.table = (SQLiteTable<T>) TableRegistry.getInstance().getTable(tableName);
    this.cursorDataExtractor = cursorDataExtractor;
    this.iterator = iterator;
    this.iterator.setContext(this);
  }

  QueryIterator<K, P> getIterator() {
    return iterator;
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public List<K> next() {
    if (iterator.hasNext()) {
      iterator.next();
      return load();
    }
    dataList = null;
    return dataList;
  }

  @Override
  public List<K> load() {
    try {
      table.open();
      dataList = table.readAll(iterator.getQuery(), cursorDataExtractor);
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
