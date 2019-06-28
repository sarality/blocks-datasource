package com.sarality.datasource;


import java.util.ArrayList;
import java.util.List;

/**
 * DataSource based on a filtered list of data.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class FilteredListDataSource<T> implements DataSource<List<T>> {

  private List<T> dataList;
  private final DataSource<List<T>> dataSource;
  private final DataFilter<T> filter;

  public FilteredListDataSource(DataSource<List<T>> dataSource, DataFilter<T> filter) {
    this.dataSource = dataSource;
    this.filter = filter;
  }

  // Implements DataSource
  @Override
  public List<T> load() {
    List<T> list = dataSource.load();
    if (list == null) {
      return null;
    }
    dataList = new ArrayList<>();
    for (T data : list) {
      if (filter.evaluate(data)) {
        dataList.add(data);
      }
    }
    return dataList;
  }

  // Implements DataSource
  @Override
  public List<T> getData() {
    return dataList;
  }
}
