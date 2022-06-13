package com.epam.tkach.carrent.entity.enums;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    ADMIN(1),
    MANAGER(2),
    CLIENT(3);

    int value;
    private Role(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public static Role getByID(int id){
        switch (id){
            case 1: return ADMIN;
            case 2: return MANAGER;
            case 3: return CLIENT;
        }
        return null;
    }

    public static List<Role> getAll(){
        List<Role> list = new ArrayList();
        for (Role role: Role.values()){
            list.add(role);
        }
        return list;
    }
}
