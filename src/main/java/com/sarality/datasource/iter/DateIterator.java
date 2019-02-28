package com.sarality.datasource.iter;

import hirondelle.date4j.DateTime;

/**
 * Iterates through a Data Source by incrementing the Date
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class DateIterator implements DataSourceIterator<DateTime> {
  private final DateTime initialDate;
  private final int increment;
  private final DateTime endDate;

  private DateTime currentDate;

  public DateIterator(DateTime initialDate, int increment, DateTime endDate) {
    this.initialDate = initialDate;
    this.increment = increment;
    this.endDate = endDate;

    this.currentDate = initialDate;
  }

  @Override
  public DateTime reset() {
    currentDate = initialDate;
    return currentDate;
  }

  @Override
  public DateTime moveToNext(int currentListSize) {
    if (hasNext(currentListSize)) {
      currentDate = getNext();
      return currentDate;
    }
    return null;
  }

  @Override
  public DateTime getCurrent() {
    return currentDate;
  }

  @Override
  public boolean hasNext(int currentListSize) {
    return endDate == null || (increment > 0 && endDate.gt(getNext())) || (increment < 0 && endDate.lteq(getNext()));
  }

  @Override
  public DateTime getNext() {
    return currentDate.plusDays(increment);
  }
}
