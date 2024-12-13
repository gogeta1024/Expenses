package org.example.expenses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.expenses.utils.FormatUtils;

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

    public String getRentFormatted()
    {
        return FormatUtils.formatPrice(rent);
    }
    public String getElectricityFormatted()
    {
        return FormatUtils.formatPrice(electricity);
    }
    public String getWaterFormatted()
    {
        return FormatUtils.formatPrice(water);
    }
    public String getGasFormatted()
    {
        return FormatUtils.formatPrice(gas);
    }
    public String getSportFormatted()
    {
        return FormatUtils.formatPrice(sport);
    }
    public String getOtherFormatted()
    {
        return FormatUtils.formatPrice(other);
    }
    public String getTotalFormatted()
    {
        Integer total = rent + electricity + water + gas + (sport != null ? sport : 0) + (other != null ? other : 0);
        return FormatUtils.formatPrice(total);
    }
}
