package com.sarality.datasource;

import com.sarality.db.common.FieldValueGetter;
import com.sarality.db.common.FieldValueSetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Source that composes a data object by combining data from multiple data sources.
 *
 * @author satya@ (Satya Puniani)
 */
public class CompositeDataSource<T, A, I> implements DataSource<List<T>> {

  private final DataSource<List<T>> dataSource;

  private DataSource<List<A>> associatedSource;

  private FieldValueGetter<T, I> parentIndexExtractor;
  private FieldValueGetter<A, I> childIndexExtractor;
  private FieldValueSetter<T, A> parentFieldValueSetter;

  private List<T> dataList;
  private Map<I, List<T>> indexedDataMap = new HashMap<>();

  public CompositeDataSource(DataSource<List<T>> dataSource) {
    this.dataSource = dataSource;
  }

  public CompositeDataSource<T, A, I> composeWith(DataSource<List<A>> associatedSource,
      FieldValueGetter<T, I> parentIndexExtractor, FieldValueGetter<A, I> associatedDataIndexExtractor,
      FieldValueSetter<T, A> setter) {

    this.associatedSource = associatedSource;
    this.parentIndexExtractor = parentIndexExtractor;
    this.childIndexExtractor = associatedDataIndexExtractor;
    this.parentFieldValueSetter = setter;

    return this;
  }

  @Override
  public List<T> load() {
    dataList = dataSource.load();

    for (T data : dataList) {
      I index = parentIndexExtractor.getValue(data);
      if (indexedDataMap.containsKey(index)) {
        indexedDataMap.put(index, new ArrayList<T>());
      }
      List<T> values = indexedDataMap.get(index);
      values.add(data);
    }

    setAssociatedData();

    return dataList;
  }

  @Override
  public List<T> getData() {
    return dataList;
  }

  private void setAssociatedData() {

    if (associatedSource == null) {
      return;
    }

    I index;
    List<T> values;
    List<A> associatedDataList = associatedSource.load();

    for (A associatedData : associatedDataList) {
      index = childIndexExtractor.getValue(associatedData);
      values = indexedDataMap.get(index);
      if (values != null) {
        for (T parentData : values) {
          parentFieldValueSetter.setValue(parentData, associatedData);
        }
      }
    }
  }
}
