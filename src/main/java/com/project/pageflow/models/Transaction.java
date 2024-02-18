package com.project.pageflow.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

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

    private Integer fine;

    @JoinColumn
    @ManyToOne
    private Book book;

    @JoinColumn(name = "stud_id")
    @ManyToOne
    private Student student;

    @JoinColumn
    @ManyToOne
    private Admin admin;

    @CreationTimestamp
    private Date createdOn;
}
