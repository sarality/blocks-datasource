package com.sarality.datasource;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * DataSource that sorts the data returned by the original Data Source
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class SortedDataSource<T> implements DataSource<List<T>> {

  private final DataSource<List<T>> dataSource;
  private final Comparator<T> comparator;

  private List<T> dataList;

  public SortedDataSource(DataSource<List<T>> dataSource, Comparator<T> comparator) {
    this.dataSource = dataSource;
    this.comparator = comparator;
  }

  @Override
  public List<T> load() {
    dataList = dataSource.load();
    Collections.sort(dataList, comparator);
    return dataList;
  }

  @Override
  public List<T> getData() {
    return dataList;
  }
}
