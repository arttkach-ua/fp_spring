package com.epam.tkach.carrent.entity.enums;

public enum CarClass {
    ECONOMY(1),
    MIDDLE(2),
    BUSINESS(3),
    PREMIUM(4);

    private int value;
    private CarClass(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CarClass getByID(int id){
        switch (id){
            case 1: return ECONOMY;
            case 2: return MIDDLE;
            case 3: return BUSINESS;
            case 4: return PREMIUM;
        }
        return null;
    }

}
