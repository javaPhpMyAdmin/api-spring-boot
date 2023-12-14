package com.expenseTracker.expenseTracker.repository;

import com.expenseTracker.expenseTracker.entities.Maker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends CrudRepository<Maker, Long> {
}
