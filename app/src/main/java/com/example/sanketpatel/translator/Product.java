package com.example.sanketpatel.translator;

public class Product {
    private	int	id;
    private	String name;
    private	String	quantity;

    public Product(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Product(int id, String name, String quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
