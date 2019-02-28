package com.sarality.datasource.iter;

import com.sarality.db.SQLiteTable;
import com.sarality.db.TableRegistry;
import com.sarality.db.cursor.CursorDataExtractor;
import com.sarality.db.query.Query;

import java.util.List;

/**
 * A DataSource that queries a Table based on Iterator.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public abstract class IterableCursorDataSource<T, K, P> implements IterableDataSource<K, P> {
  protected final SQLiteTable<T> table;
  private final CursorDataExtractor<K> cursorDataExtractor;
  private final DataSourceIterator<P> iterator;

  protected List<K> dataList;

  public IterableCursorDataSource(String tableName, CursorDataExtractor<K> cursorDataExtractor,
      DataSourceIterator<P> iterator) {
    this.table = (SQLiteTable<T>) TableRegistry.getInstance().getTable(tableName);
    this.cursorDataExtractor = cursorDataExtractor;
    this.iterator = iterator;
  }

  protected DataSourceIterator<P> getIterator() {
    return iterator;
  }

  public abstract Query getQuery();

  @Override
  public boolean hasNext() {
    return iterator.hasNext(dataList == null ? 0 : dataList.size());
  }

  @Override
  public List<K> next() {
    iterator.moveToNext(dataList == null ? 0 : dataList.size());
    return load();
  }

  @Override
  public List<K> load() {
    try {
      table.open();
      dataList = table.readAll(getQuery(), cursorDataExtractor);
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
