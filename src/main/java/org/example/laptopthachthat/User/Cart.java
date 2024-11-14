package org.example.laptopthachthat.User;

import java.math.BigDecimal;

public class Cart {
    private String name ;
    private int  quantity;
    private Double price;
    private int cartID ;


    public Cart(String name, int quantity, Double price, int cartID) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.cartID = cartID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

}
