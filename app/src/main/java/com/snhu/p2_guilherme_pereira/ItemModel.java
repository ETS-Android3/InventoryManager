package com.snhu.p2_guilherme_pereira;

public class ItemModel {
    private int id;
    private String name;
    private int amount;

    /*** Constructors ***/
    public ItemModel(int id, String name, int amount){
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public ItemModel(){

    }

    /*** Getters & Setters ***/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    /*** Functions ***/
    @Override
    public String toString() {
        return  "[ Item Model ]\n" +
                "[ ID: " + getId() + " ]\n" +
                "[ Name: " + getName() + " ]\n" +
                "[ Qty: " + getAmount() + " ]";
    }
}
