package com.sarality.datasource.iter;

import com.sarality.datasource.DataSource;

import java.util.List;

/**
 * Interface for classes that Iterate through a DataSource in blocks or pages.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface IterableDataSource<T, P> extends DataSource<List<T>> {

  boolean hasNext();

  List<T> next();
}
