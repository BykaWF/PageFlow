package com.project.pageflow.dto;

import com.project.pageflow.models.ShippingAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddressDto {

    @NotBlank
    String streetAddress;

    @NotBlank
    private String city;

    @NotNull
    private Integer postalCode;

    @NotBlank
    private String country;

    public ShippingAddress toShippingAddress(){
        return ShippingAddress.builder()
                .streetAddress(this.streetAddress)
                .city(this.city)
                .country(this.country)
                .postalCode(this.postalCode)
                .build();
    }
}
