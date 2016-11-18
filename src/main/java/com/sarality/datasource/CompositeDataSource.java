package com.sarality.datasource;

import com.sarality.db.common.FieldValueGetter;
import com.sarality.db.common.ChildDataSetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Source that composes a data object by combining data from multiple data sources.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class CompositeDataSource<T, A, I> implements DataSource<List<T>> {

  private Map<I, List<T>> parentDataMap = new HashMap<>();

  private final DataSource<List<T>> dataSource;

  private DataSource<List<A>> associatedSource;

  private FieldValueGetter<T, I> parentIndexExtractor;
  private FieldValueGetter<A, I> childIndexExtractor;
  private ChildDataSetter<T, A> setter;

  private List<T> dataList;
  private Map<I, List<T>> indexedDataMap = new HashMap<>();

  public CompositeDataSource(DataSource<List<T>> dataSource) {
    this.dataSource = dataSource;
  }

  public CompositeDataSource<T, A, I> composeWith(DataSource<List<A>> associatedSource,
      FieldValueGetter<T, I> parentIndexExtractor, FieldValueGetter<A, I> associatedDataIndexExtractor,
      ChildDataSetter<T, A> setter) {

    this.associatedSource = associatedSource;
    this.parentIndexExtractor = parentIndexExtractor;
    this.childIndexExtractor = associatedDataIndexExtractor;
    this.setter = setter;

    return this;
  }

  @Override
  public List<T> load() {
    dataList = dataSource.load();
    I index;
    List<T> values;

    for (T data : dataList) {
      index = parentIndexExtractor.getValue(data);
      values = indexedDataMap.get(index);
      if (values == null) {
        values = new ArrayList<T>();
        indexedDataMap.put(index, values);
      }
      values.add(data);
    }

    setAssociatedData();

    return dataList;
  }

  @Override public List<T> getData() {
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
          setter.setChildData(parentData, associatedData);
        }
      }
    }
  }
}
