package com.sarality.datasource.iter;

import com.sarality.datasource.DataSource;

import java.util.List;

/**
 * Add description here
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface IterableDataSource<T, P> extends DataSource<List<T>> {

  boolean hasNext();

  List<T> next();

}
