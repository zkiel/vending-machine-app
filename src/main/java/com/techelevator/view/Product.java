package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

public class Product implements Vendable {
    //
    // Changes on clases: variable names commented purchase menu item function below.
    //
    private static File inventoryFile = new File("./vendingmachine.csv");
    private String code;
    private String name;
    private BigDecimal price;
    private Vendable category;
    private int quantitySold;
    private int currentStock;
    private static final int DEFAULT_Quantity = 5;

    public String getMessage() {
        return message;
    }

    private String message;


    public String getCode() {
        return code;
    }


    public String getName() {
        return name;
    }


    public BigDecimal getPrice() {
        return price;
    }


    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public int getCurrentStock() {
        return currentStock;
    }


    public void setCategory(Vendable category) {
        this.category = category;
    } //not needed so far

    public Product(String code, String name, BigDecimal price) { //added qty
        this.code = code;
        this.name = name;
        this.price = price;
        this.category = category;
        currentStock = DEFAULT_Quantity;
        quantitySold = 0;
    }


    @Override
    public String sound() {
        if (getCode().charAt(0) == 'A') {
            return "Crunch crunch, Yum!";
        } else if (getCode().charAt(0) == 'B') {
            return "Munch, munch, Yum!";
        } else if (getCode().charAt(0) == 'C') {
            return "Slurp, slurp, Yum!";
        } else if (getCode().charAt(0) == 'D') {
            return "Chew, chew, Yum!";
        } else
            return null;
    }

    public String vend() {
        currentStock -= 1;
        return sound();
    }
}