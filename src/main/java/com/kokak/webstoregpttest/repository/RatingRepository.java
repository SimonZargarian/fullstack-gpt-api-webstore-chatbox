package com.kokak.webstoregpttest.repository;

import com.kokak.webstoregpttest.object.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
}
