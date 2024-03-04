package com.project.pageflow.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private String cardNumber;

    private String cardHolderName;

    private Integer expirationMonth;

    private Integer expirationYear;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


}
