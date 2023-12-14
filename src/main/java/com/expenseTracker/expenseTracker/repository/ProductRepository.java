package com.expenseTracker.expenseTracker.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.expenseTracker.expenseTracker.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "SELECT p FROM Pruduct p WHERE p.price BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Product> findProductByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
