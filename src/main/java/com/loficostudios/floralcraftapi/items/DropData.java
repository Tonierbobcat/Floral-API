package com.loficostudios.floralcraftapi.items;

@Deprecated
public class DropData {
    private final double rate;
    private final int minAmount;
    private final int maxAmount;

    public DropData(double rate, int minAmount, int maxAmount) {

        this.rate = rate;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public double getRate() {
        return rate;
    }

    public int getMinAmount() {
        return minAmount;
    }
}
