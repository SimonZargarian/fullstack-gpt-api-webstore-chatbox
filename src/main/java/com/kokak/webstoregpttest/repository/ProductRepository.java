package com.kokak.webstoregpttest.repository;

import com.kokak.webstoregpttest.object.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
