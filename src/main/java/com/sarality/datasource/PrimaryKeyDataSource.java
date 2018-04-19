package com.sarality.datasource;

import com.sarality.db.Column;
import com.sarality.db.Table;
import com.sarality.db.query.SimpleQueryBuilder;

import java.util.List;

/**
 * Generic data source for T given primary Id
 *
 * @author satya (satya puniani)
 */

public class PrimaryKeyDataSource<T> implements DataSource<T> {

  private final Table<T> table;
  private final Column entityIdColumn;
  private final String entityType;
  private Long entityId;
  private T entityData;

  public PrimaryKeyDataSource(Table<T> table, Column entityIdColumn, String entityType) {
    this.table = table;
    this.entityIdColumn = entityIdColumn;
    this.entityType = entityType;
  }

  public T load(Long entityId) {
    this.entityId = entityId;
    return load();
  }

  @Override
  public T load() {
    if (entityId == null) {
      return null;
    }

    entityData = null;

    try {
      table.open();

      List<T> dataList = table.readAll(
          new SimpleQueryBuilder().withFilter(entityIdColumn, entityId)
              .build());

      // no result is a valid scenario
      if ((dataList == null) || (dataList.size() == 0)) {
        return null;
      }

      if (dataList.size() > 1) {
        throw new IllegalStateException("More than one " + entityType + " exists with Id " + entityId);
      }

      this.entityData = dataList.get(0);
      return entityData;

    } finally {
      table.close();
    }
  }

  @Override
  public T getData() {
    return entityData;
  }

}
