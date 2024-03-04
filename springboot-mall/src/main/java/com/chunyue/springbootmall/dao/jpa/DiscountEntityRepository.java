package com.chunyue.springbootmall.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface DiscountEntityRepository extends JpaRepository<DiscountEntity, Long> {
    Optional<DiscountEntity> findDiscountEntitiesByDiscountCodeLikeAndInnerCodeLikeAndStartedDateGreaterThanEqualAndEndDateLessThanEqual(
            String discountCode,
            String innerCode,
            LocalDate startedDate,
            LocalDate endDate
    );
}
