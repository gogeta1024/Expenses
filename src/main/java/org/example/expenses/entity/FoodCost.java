package org.example.expenses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
    @Table(name = "food_cost") // Tên bảng trong cơ sở dữ liệu
    public class FoodCost {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
        private Long id;

        @Column(name = "supermarket", nullable = false, length = 100) // Tên siêu thị
        private String supermarket;

        @Column(name = "purchase_date", nullable = false) // Ngày tháng mua
        private LocalDate purchaseDate;

        @Column(name = "food_type", nullable = false, length = 100) // Loại đồ ăn
        private String foodType;

        @Column(name = "amount", nullable = false, precision = 10, scale = 2) // Số tiền chi cho đồ ăn
        private BigDecimal amount;

}
