package com.project.pageflow.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stud_id")
    private Integer id;
    @Column(name = "stud_first_name")
    private String firstName;
    @Column(name = "stud_second_name")
    private String secondName;

    @Column(unique = true, nullable = false, name = "stud_email")
    private String email;

    @Column(unique = true, nullable = false)
    private String rollNumber;

    @Column(name = "stud_age")
    private Integer age;

    @OneToMany(mappedBy = "student")
    private List<Transaction> transactionList;

    @OneToOne(mappedBy = "student")
    private ShoppingSession shoppingSession;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<ShippingAddress> shippingAddress;

    @OneToMany(mappedBy = "student" ,cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods;

    @OneToOne
    @JoinColumn
    private SecuredUser securedUser;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;
}
