package com.tdp2.ghsz.tp0.util;

import java.math.BigDecimal;
import java.math.MathContext;

public class DoubleRounder {
    public static double round(double d, int figs) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.round(new MathContext(figs));
        return bd.doubleValue();
    }
}
