package com.example.sanketpatel.translator;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String quantity;
    private String uri;

    public Product() {
    }

    public Product(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Product(int id, String name, String quantity, String uri) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.uri = uri;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
