package com.sarality.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * Datasource that transforms data from one type to another
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class TransformListDataSource<K, T> implements DataSource<List<T>> {

  private final DataSource<List<K>> listDataSource;
  private final Transformer<K, T> transformer;

  private List<T> dataList;

  public TransformListDataSource(DataSource<List<K>> listDataSource,
      Transformer<K, T> transformer) {
    this.listDataSource = listDataSource;
    this.transformer = transformer;
  }

  @Override
  public List<T> load() {
    if (listDataSource == null) {
      dataList = null;
      return null;
    }
    List<K> sourceList = listDataSource.load();
    if (sourceList == null) {
      dataList = null;
      return null;
    }
    dataList = new ArrayList<>();
    for (K source: sourceList) {
      dataList.add(transformer.transform(source));
    }
    return dataList;
  }

  @Override
  public List<T> getData() {
    return dataList;
  }
}
