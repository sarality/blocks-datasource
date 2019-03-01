package com.sarality.datasource.iter;

import com.sarality.datasource.DataSource;
import com.sarality.db.query.Query;

import java.util.List;

/**
 * Interface for classes that generate Queries for an Iterable DataSource.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface QueryIterator<T, P> {

  void setContext(DataSource<List<T>> dataSource);

  boolean hasNext();

  P next();

  Query getQuery();

  P reset();
}
