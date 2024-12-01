package org.example.expenses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDTO
{
    private Long id;

    @NotBlank(message = "{MS1_REQUIRED}")
    private String month;
    @NotNull(message = "{MS1_REQUIRED}")
    private Integer rent;
    @NotNull(message = "{MS1_REQUIRED}")
    private Integer electricity;
    @NotNull(message = "{MS1_REQUIRED}")
    private Integer water;
    @NotNull(message = "{MS1_REQUIRED}")
    private Integer gas;
    private Integer sport;
    private Integer other;

}
