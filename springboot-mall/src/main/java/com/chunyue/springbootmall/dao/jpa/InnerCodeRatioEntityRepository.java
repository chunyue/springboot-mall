package com.chunyue.springbootmall.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface InnerCodeRatioEntityRepository  extends JpaRepository<InnerCodeRatioEntity, Long> {

    InnerCodeRatioEntity findByInnerCode(String innerCode);
}
