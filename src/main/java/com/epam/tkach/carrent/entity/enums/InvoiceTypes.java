package com.epam.tkach.carrent.entity.enums;

public enum InvoiceTypes {
    RENT(1),
    DAMAGE(2);

    private int value;

    InvoiceTypes(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    public static InvoiceTypes getByID(int id){
        switch (id){
            case 1: return RENT;
            case 2: return DAMAGE;
            default: return null;
        }
    }
}
