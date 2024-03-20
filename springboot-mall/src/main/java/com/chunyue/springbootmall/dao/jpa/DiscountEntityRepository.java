package com.chunyue.springbootmall.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DiscountEntityRepository extends JpaRepository<DiscountEntity, Long>, JpaSpecificationExecutor<DiscountEntity> {
    Page<DiscountEntity> findAll(Specification specification, Pageable pageable);

    DiscountEntity findByDiscountCode(String discountCode);

}
