package com.project.pageflow.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(name = "street_address")
    String streetAddress;

    private String city;

    @Column(name = "postal_code")
    private Integer postalCode;

    private String country;

    @ManyToOne
    @JoinColumn(name = "stud_id")
    private Student student;

}
