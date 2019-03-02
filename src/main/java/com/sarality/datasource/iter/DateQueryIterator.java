package com.sarality.datasource.iter;

import com.sarality.datasource.DataSource;

import java.util.List;

import hirondelle.date4j.DateTime;

/**
 * Iterates through queries for a Data Source by incrementing the Date used by the Query.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public abstract class DateQueryIterator<T> implements QueryIterator<T, DateTime> {
  private final DateTime initialDate;
  private final int dateIncrement;
  private final DateTime endDate;

  private DataSource<List<T>> dataSource;
  private DateTime currentDate;

  public DateQueryIterator(DateTime initialDate, int dateIncrement, DateTime endDate) {
    this.initialDate = initialDate;
    this.dateIncrement = dateIncrement;
    this.endDate = endDate;
    this.currentDate = null;
  }


  @Override
  public void setContext(DataSource<List<T>> dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public boolean hasNext() {
    if (currentDate == null) {
      return true;
    }
    return (dateIncrement > 0 && endDate.gt(getNextDate())) || (dateIncrement < 0 && endDate.lteq(getNextDate()));
  }

  @Override
  public DateTime next() {
    if (hasNext()) {
      currentDate = getNextDate();
      return currentDate;
    }
    return null;
  }

  @Override
  public DateTime reset() {
    currentDate = initialDate;
    return currentDate;
  }

  protected DateTime getCurrentDate() {
    return currentDate;
  }

  protected DateTime getNextDate() {
    if (currentDate == null) {
      return initialDate;
    }
    return currentDate.plusDays(dateIncrement);
  }
}
