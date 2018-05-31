package com.sarality.datasource;

import com.sarality.db.Column;
import com.sarality.db.Table;
import com.sarality.db.TableRegistry;
import com.sarality.db.query.SimpleQueryBuilder;

import java.util.List;

/**
 * Generic data source for T given primary Id
 *
 * @author satya (satya puniani)
 */

public class PrimaryKeyDataSource<T> implements DataSource<T> {

  private final String tableName;
  private final Column entityIdColumn;
  private final Long entityId;
  private T entityData;

  public PrimaryKeyDataSource(String tableName, Column entityIdColumn, Long entityId) {
    assert (entityId != null);
    this.tableName = tableName;
    this.entityIdColumn = entityIdColumn;
    this.entityId = entityId;
  }

  @Override
  public T load() {

    Table<T> table = TableRegistry.getInstance().getTable(tableName);
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
        throw new IllegalStateException("More than one " + table.getName() + " exists with Id " + entityId);
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
