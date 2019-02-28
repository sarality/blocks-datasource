package com.sarality.datasource.iter;

/**
 * Interface for classes that determine how to iterate thru an Iterable DataSource
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface DataSourceIterator<P> {

  P getCurrent();

  boolean hasNext(int listSize);

  P moveToNext(int currentListSize);

  P getNext();

  P reset();
}
