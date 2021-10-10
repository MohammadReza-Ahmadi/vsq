package com.vosouq.commons.model;

public class BinaryDataHolder<FirstType, SecondType> {

    private final FirstType firstType;
    private final SecondType secondType;

    public BinaryDataHolder(FirstType firstType, SecondType secondType) {
        this.firstType = firstType;
        this.secondType = secondType;
    }

    public FirstType getFirstType() {
        return firstType;
    }

    public SecondType getSecondType() {
        return secondType;
    }
}
