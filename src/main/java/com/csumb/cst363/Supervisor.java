package com.csumb.cst363;
/*
 * This file is currently not in use
 * We thought this project was going to require all tables we originally created
 */
public class Supervisor {
    private int ID;
    private String name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Supervisor{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}
