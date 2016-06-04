package com.sarality.datasource;

/**
 * Interface for all sources of data
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface DataSource<T> {

  /**
   * Load data for an underlying data store of API.
   *
   * @return The data that was loaded.
   */
  T load();

  /**
   * @return Data that was loaded in the {@link #load()} call.
   */
  T getData();
}
