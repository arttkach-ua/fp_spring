package com.epam.tkach.carrent.entity.enums;

public enum TransmissionTypes {
    MANUAL(1),
    AUTOMATIC(2);

    int value;

    private TransmissionTypes(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TransmissionTypes getByID(int id){
        switch (id){
            case 1: return MANUAL;
            case 2: return AUTOMATIC;
        }
        return null;
    }
}
