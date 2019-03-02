package com.sarality.datasource.iter;

import com.sarality.datasource.DataSource;

import java.util.List;

/**
 * Iterates through queries for a Data Source by chnaging the Page offset for the Query.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public abstract class PageQueryIterator<T> implements QueryIterator<T, Integer> {
  private final int initialOffset;
  private final int pageSize;

  private DataSource<List<T>> dataSource;
  private Integer offset;

  public PageQueryIterator(int initialOffset, int pageSize) {
    this.initialOffset = initialOffset;
    this.pageSize = pageSize;
    this.offset = null;
  }

  @Override
  public void setContext(DataSource<List<T>> dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public boolean hasNext() {
    // Offset is initially null
    if (offset == null) {
      return true;
    }
    List<T> dataList = dataSource.getData();
    return (dataList != null && (dataList.size() == pageSize));
  }

  @Override
  public Integer next() {
    offset = getNextOffset();
    return offset;
  }

  @Override
  public Integer reset() {
    offset = initialOffset;
    return offset;
  }

  protected int getCurrentOffSet() {
    return offset;
  }

  protected int getPageSize() {
    return pageSize;
  }

  protected int getNextOffset() {
    if (offset == null) {
      return initialOffset;
    }
    return offset + pageSize;
  }
}
