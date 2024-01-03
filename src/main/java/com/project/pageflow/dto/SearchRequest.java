package com.project.pageflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {

    @NotBlank
    private String searchKey;

    @NotBlank
    private String searchValue;

}
