package com.ntech.dispatchloadbalancer.model;

//compareTo is not declared specifically as it compares based on ordinal/declared value
public enum Priority {
    LOW, // ordinal value : 0
    MEDIUM, // ordinal value: 1
    HIGH // ordinal value :2

    //Enum order: LOW < MEDIUM < HIGH
}
