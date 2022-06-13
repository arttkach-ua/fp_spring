package com.epam.tkach.carrent.entity.enums;

import java.util.ArrayList;
import java.util.List;

public enum FuelTypes {
    DIESEl(1),
    BENZINE(2),
    HYBRID(3),
    ELECTRO(4);

    private int value;

    private FuelTypes(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public static FuelTypes getByID(int id){
        switch (id){
            case 1: return DIESEl;
            case 2: return BENZINE;
            case 3: return HYBRID;
            case 4: return ELECTRO;
        }
        return null;
    }

    public static List<FuelTypes> getAll(){
        List<FuelTypes> list = new ArrayList();
        for (FuelTypes fuelType: FuelTypes.values()){
            list.add(fuelType);
        }
        return list;
    }
}
