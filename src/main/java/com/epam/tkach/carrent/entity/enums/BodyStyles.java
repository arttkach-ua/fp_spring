package com.epam.tkach.carrent.entity.enums;

public enum BodyStyles {
    SEDAN(1),
    COUPE(2),
    HATCHBACK(3),
    MINIVAN(4),
    SUV(5),
    COMBI(6);

    int value;

    private BodyStyles(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BodyStyles getByID(int id){
        switch (id){
            case 1: return SEDAN;
            case 2: return COUPE;
            case 3: return HATCHBACK;
            case 4: return MINIVAN;
            case 5: return SUV;
            case 6: return COMBI;
        }
        return null;
    }
}
