package org.example.expenses.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyDTO
{
    private Long id;

    @NotNull(message = "{MS1_REQUIRED}")
    private LocalDate purchaseDate;

    private String supermarket;

    @NotBlank(message = "{MS1_REQUIRED}")
    private String type;

    @NotBlank(message = "{MS1_REQUIRED}")
    private String name;

    private Integer weight;

    @NotNull(message = "{MS2_REQUIRED}")
    @Min(value = 1, message = "{MS2_REQUIRED}")
    private Integer price;

    @NotNull(message = "{MS2_REQUIRED}")
    @Min(value = 1, message = "{MS2_REQUIRED}")
    private Integer quantity=1;

}
