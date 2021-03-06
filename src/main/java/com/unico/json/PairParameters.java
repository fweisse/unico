package com.unico.json;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PairParameters {
private int a, b;


    @JsonCreator
    public PairParameters(@JsonProperty("a") int a,
                          @JsonProperty("b") int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        return "PairParameters{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }


}

