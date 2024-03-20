package com.chunyue.springbootmall.dao.jpa;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InnerCodeRatioEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence-generator", sequenceName = "discount_ratio_sequence")
    Long id;

    @Column(name = "inner_code")
    String innerCode;

    @Column(name = "discount_ratio", precision = 15, scale = 6)
    BigDecimal discountRatio;

}
