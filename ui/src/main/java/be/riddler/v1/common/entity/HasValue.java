package be.riddler.v1.common.entity;

/**
 * HasValue
 *
 * @author dnoulet
 * @version 1.0.0 02/06/2026
 */
public interface HasValue<T> {
    boolean isSame(T value);
}
