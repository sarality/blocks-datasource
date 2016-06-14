package com.sarality.datasource;

import android.database.Cursor;
import android.net.Uri;

import com.sarality.db.query.Query;

/**
 * Interface for all sources of data from a Content Provider
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface ContentProviderSource<T> {

  /**
   * @return Registered Uri for the Content Provider.
   */
  Uri getContentProviderUri();

  /**
   * @return Query to run on the Content Provider.
   */
  Query getQuery();

  /**
   * @return Data object for the current value at the cursor.
   */
  T getData(Cursor cursor);

}
