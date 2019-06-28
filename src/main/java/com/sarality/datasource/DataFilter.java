package com.sarality.datasource;

/**
 * Interface for classes that evaluate a Filter
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface DataFilter<T> {

  boolean evaluate(T data);
}
