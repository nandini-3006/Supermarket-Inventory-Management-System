package com.nandini.inventory.sales;

import com.nandini.inventory.products.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SalesRepository  extends JpaRepository<SalesEntity,Long> {
    Optional<SalesEntity> findByNameAndBrandAndExpiry(String name, String brand, LocalDate Expiry);
}
