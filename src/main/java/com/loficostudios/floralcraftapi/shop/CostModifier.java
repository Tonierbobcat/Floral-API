package com.loficostudios.floralcraftapi.shop;

public class CostModifier {

    public enum Operation {
        ADD,
        MULTIPLY
    }

    private final double value;
    private final Operation operation;
    private final String id;
    public CostModifier(String id, double value, Operation operation) {
        this.id = id;
        this.value = value;
        this.operation = operation;
    }

    public double getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getId() {
        return id;
    }
}
