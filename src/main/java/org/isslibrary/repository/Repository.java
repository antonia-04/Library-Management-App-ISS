package org.isslibrary.repository;


import org.isslibrary.domain.Entity;

/**
 * CRUD operations repository interface
 *
 * @param <ID> - type of entity ID
 * @param <E>  - type of entity
 */
public interface Repository<ID, E extends Entity<ID>> {

    Iterable<E> findAll();

}
