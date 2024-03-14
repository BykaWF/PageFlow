package com.project.pageflow.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String transactionId;

    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalOrder;

    @JoinColumn(name = "stud_id")
    @ManyToOne
    private Student student;

    @JoinColumn
    @ManyToOne
    private Admin admin;

    @ManyToOne
    private PaymentMethod paymentMethod;

    @OneToOne
    private ShippingAddress shippingAddress;

    @CreationTimestamp
    private Date createdOn;
}
