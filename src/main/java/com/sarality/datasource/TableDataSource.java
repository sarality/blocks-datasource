package com.sarality.datasource;

import com.sarality.db.Table;
import com.sarality.db.TableRegistry;
import com.sarality.db.query.Query;

import java.util.List;

/**
 * A Datasource that reads data from a Table.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class TableDataSource<T> implements DataSource<List<T>> {

  private final Table<T> table;
  private final Query query;

  private List<T> dataList;

  @SuppressWarnings("unchecked")
  public TableDataSource(TableRegistry registry, String tableName, Query query) {
    this((Table<T>) registry.getTable(tableName), query);
  }

  public TableDataSource(Table<T> table, Query query) {
    this.table = table;
    this.query = query;
  }

  @Override
  public List<T> load() {
    dataList = table.readAll(query);
    return dataList;
  }

  @Override
  public List<T> getData() {
    return dataList;
  }
}
