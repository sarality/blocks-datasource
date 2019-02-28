package com.sarality.datasource.iter;

/**
 * Iterates through a Data Source one page at a time.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class PageIterator implements DataSourceIterator<Integer> {
  private final int initialOffset;
  private final int pageSize;
  private final Integer maxOffset;
  private int offset;

  public PageIterator(int initialOffset, int pageSize, Integer maxOffset) {
    this.initialOffset = initialOffset;
    this.pageSize = pageSize;
    this.offset = initialOffset;
    this.maxOffset = maxOffset;
  }

  @Override
  public Integer reset() {
    offset = initialOffset;
    return offset;
  }

  @Override
  public Integer moveToNext(int currentListSize) {
    offset = getNext();
    return offset;
  }

  @Override
  public Integer getCurrent() {
    return offset;
  }

  @Override
  public boolean hasNext(int currentListSize) {
    return (currentListSize == pageSize) && (maxOffset == null || maxOffset < getNext());
  }

  public Integer getNext() {
    return offset = offset + pageSize;
  }
}
