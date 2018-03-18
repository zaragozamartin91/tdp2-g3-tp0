package com.tdp2.ghsz.tp0.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoubleRounderTest {
    @Test
    public void round() throws Exception {
        double d = 21.2345;
        int figs = 3;
        Double round = DoubleRounder.round(d, figs);
        assertEquals("21.2" , round.toString());
    }
}