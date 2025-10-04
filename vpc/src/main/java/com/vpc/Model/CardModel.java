package com.vpc.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
public class CardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String phoneNo;
    String email;
    String accountNo;
    BigDecimal balance;
    BigDecimal spent = BigDecimal.ZERO;
    BigDecimal totalLimit = new BigDecimal(1000);
    @Value("${boolean.locked:false}")
    boolean locked;
    LocalDate expiry = LocalDate.now().plusYears(5);
}
