package com.epam.tkach.carrent.entity.enums;

public enum OrderStatuses {
    NEW(1),
    APPROVED(2),
    CANCELED(3),
    INPROGRESS(4),
    HASDAMAGE(5),
    COMPLETE(6);


    private int value;

    private OrderStatuses(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static OrderStatuses getByID(int id){
        switch (id){
            case 1: return NEW;
            case 2: return APPROVED;
            case 3: return CANCELED;
            case 4: return INPROGRESS;
            case 5: return HASDAMAGE;
            case 6: return COMPLETE;
        }
        return null;
    }
}
