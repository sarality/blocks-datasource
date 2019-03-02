package com.sarality.datasource;

/**
 * Interface for class that transforms data from one type to another.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface Transformer<I, O> {
  O transform(I input);
}
