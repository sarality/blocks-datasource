package com.sarality.datasource;

import android.database.Cursor;
import android.net.Uri;

import com.sarality.db.cursor.CursorDataExtractor;
import com.sarality.db.query.Query;

/**
 * Generic implementation of ContentProvider based DataSource.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class GenericContentProviderSource<T> implements ContentProviderSource<T> {

  private final Uri contentProviderUri;
  private final Query query;
  private final CursorDataExtractor<T> extractor;

  public GenericContentProviderSource(Uri contentProviderUri, Query query,
      CursorDataExtractor<T> extractor) {
    this.contentProviderUri = contentProviderUri;
    this.query = query;
    this.extractor = extractor;
  }

  @Override
  public Uri getContentProviderUri() {
    return contentProviderUri;
  }

  @Override
  public Query getQuery() {
    return query;
  }

  @Override
  public T getData(Cursor cursor) {
    return extractor.extract(cursor, query);
  }
}
