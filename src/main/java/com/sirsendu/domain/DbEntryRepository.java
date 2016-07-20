package com.sirsendu.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sirsendu Konar
 *
 */
@Repository
public interface DbEntryRepository extends CrudRepository<DbEntry, Integer> {

}
