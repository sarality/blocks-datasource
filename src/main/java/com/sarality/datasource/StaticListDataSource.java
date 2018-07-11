package com.sarality.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource based of a static list of data.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class StaticListDataSource<T> implements DataSource<List<T>> {

  private final List<T> dataList;

  public StaticListDataSource(List<T> list) {
    if (list != null) {
      dataList = new ArrayList<>();
      dataList.addAll(list);
    } else {
      dataList = null;
    }
  }

  // Implements DataSource
  @Override
  public List<T> load() {
    return dataList;
  }

  // Implements DataSource
  @Override
  public List<T> getData() {
    return dataList;
  }
}
