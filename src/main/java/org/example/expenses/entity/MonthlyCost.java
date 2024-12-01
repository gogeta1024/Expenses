package org.example.expenses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monthly_cost") // Tên bảng trong cơ sở dữ liệu
public class MonthlyCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    private Long id;

    @Column(name = "month", nullable = false) // Tháng
    private String month;

    @Column(name = "rent", nullable = false) // Tiền nhà
    private Integer rent;

    @Column(name = "electricity", nullable = false) // Tiền điện
    private Integer electricity;

    @Column(name = "water", nullable = false) // Tiền nước
    private Integer water;

    @Column(name = "gas", nullable = false) // Tiền ga
    private Integer gas;

    @Column(name = "sport") // Tiền đá bóng
    private Integer sport;

    @Column(name = "other") // Chi phí khác
    private Integer other;
}
