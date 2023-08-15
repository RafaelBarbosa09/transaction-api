package com.infnet.transactionapi.infrastructure.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(name = "transaction_time", nullable = false)
    private Date transactionTime;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
