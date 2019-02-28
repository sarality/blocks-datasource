package com.sarality.datasource.iter;

import java.util.List;

/**
 * A DataSource that queries a Table based on Iterator.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public abstract class IterableTableDataSource<T, P> extends IterableCursorDataSource<T, T, P> {

  public IterableTableDataSource(String tableName, DataSourceIterator<P> iterator) {
    super(tableName, null, iterator);
  }

  @Override
  public List<T> load() {
    try {
      table.open();
      dataList = table.readAll(getQuery());
      return dataList;
    } finally {
      table.close();
    }
  }
}
