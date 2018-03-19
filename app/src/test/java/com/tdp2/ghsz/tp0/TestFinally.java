package com.tdp2.ghsz.tp0;

import org.junit.Test;

/**
 * Created by martin on 3/19/2018.
 */

public class TestFinally {
    @Test
    public void testFinally() {
        try{
            if(1<0) return;
        } finally {
            System.out.println("FINALLY");
        }
    }
}
