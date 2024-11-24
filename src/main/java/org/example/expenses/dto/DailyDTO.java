package org.example.expenses.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyDTO
{
    private Long id;
    private LocalDate purchaseDate;
    private String supermarket;
    private String type;
    private String name;
    private Integer weight;
    private Integer price;
    private Integer quantity=1;

}
