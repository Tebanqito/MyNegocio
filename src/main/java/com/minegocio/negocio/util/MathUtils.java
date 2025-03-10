package com.minegocio.negocio.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static double redondearPrecio(double precio) {
        BigDecimal bd = new BigDecimal(precio).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}