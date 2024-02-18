package com.project.pageflow.dto;

import com.project.pageflow.models.OrderType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiateOrderRequest {

    @NotNull
    private String studentRollNumber;

    @NotNull
    private Integer bookId;

    @NotNull
    private Integer adminId;

    @NotNull
    private OrderType orderType;

}
