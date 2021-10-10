package com.vosouq.commons.model;

public class TernaryDataHolder<FirstType, SecondType, ThirdType> {

    private final FirstType firstType;
    private final SecondType secondType;
    private final ThirdType thirdType;

    public TernaryDataHolder(FirstType firstType, SecondType secondType, ThirdType thirdType) {
        this.firstType = firstType;
        this.secondType = secondType;
        this.thirdType = thirdType;
    }

    public FirstType getFirstType() {
        return firstType;
    }

    public SecondType getSecondType() {
        return secondType;
    }

    public ThirdType getThirdType() {
        return thirdType;
    }
}
