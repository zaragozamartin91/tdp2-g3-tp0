package com.tdp2.ghsz.tp0.util;

@FunctionalInterface
public interface Procedure<T> {
    void run(T data);
}
