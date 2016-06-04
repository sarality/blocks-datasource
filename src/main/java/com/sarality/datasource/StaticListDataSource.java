package com.sarality.datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DataSource based of a static list of data.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class StaticListDataSource<T> implements DataSource<List<T>> {

  private final List<T> dataList;

  public StaticListDataSource(T... data) {
    dataList = new ArrayList<>();
    if (data != null) {
      Collections.addAll(dataList, data);
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
