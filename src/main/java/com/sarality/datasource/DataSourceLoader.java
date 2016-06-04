package com.sarality.datasource;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Task that loads the data asynchronously.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class DataSourceLoader<T> extends AsyncTaskLoader<T> {

  private final DataSource<T> dataSource;

  /**
   * Constructor
   *
   * @param context Context for the AsyncTask.
   * @param dataSource DataSource to load the data from.
   */
  public DataSourceLoader(Context context, DataSource<T> dataSource) {
    super(context);
    this.dataSource = dataSource;
  }

  /**
   * Implements AsyncTaskLoader and is called to load the data from the background thread.
   *
   * @return Data loaded from the datasource.
   */
  @Override
  public T loadInBackground() {
    dataSource.load();
    return dataSource.getData();
  }
}
