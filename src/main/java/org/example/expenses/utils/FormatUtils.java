package org.example.expenses.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {

    public static String formatPrice(Integer value) {
        if (value == null) {
            return "0";
        }

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        // Đặt số chữ số thập phân tối đa là 2
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(0); // Để không hiển thị .00 nếu không cần thiết

        return numberFormat.format(value);
    }

}

