package com.microfinanceBank.Issues.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplainDto {
    @NotNull
    private Long accountNumber;
    @NotNull
    @NotBlank
    private String issue;
}
