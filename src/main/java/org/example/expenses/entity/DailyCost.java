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
    @Table(name = "daily_cost") // Tên bảng trong cơ sở dữ liệu
    public class DailyCost {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
        private Long id;

        @Column(name = "purchase_date") // Ngày tháng mua
        private LocalDate purchaseDate;

        @Column(name = "supermarket", length = 100) // Tên siêu thị
        private String supermarket;

        @Column(name = "type", length = 100) // Category
        private String type;

        @Column(name = "name", nullable = false, length = 100) // Tên
        private String name;

        @Column(name = "weight", length = 100) // khối lượng
        private Integer weight;

        @Column(name = "price", nullable = false) // Giá Tiền
        private Integer price;

        @Column(name="quantity")
        private Integer quantity=1;

}
