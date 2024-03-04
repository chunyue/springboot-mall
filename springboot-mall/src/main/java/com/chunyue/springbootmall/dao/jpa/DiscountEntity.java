package com.chunyue.springbootmall.dao.jpa;

import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "discount_code_record", schema = "mall")
public class DiscountEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence-generator", sequenceName = "discount_sequence")
    Long id;

    @Column(name = "discount_code")
    String discountCode;

    @Column(name = "inner_code")
    String innerCode;

    @Column(name = "started_date")
    LocalDate startedDate;

    @Column(name = "end_date")
    LocalDate endDate;

    @Column(name = "remark")
    String remark;

}