package edu.temple.lab10;

import java.io.Serializable;

/**
 * Created by GyunCho on 4/30/17.
 */

public class Stock implements Serializable {
    private String companyName;
    private String symbol;
    private double price;

    public Stock(String companyName, String symbol, double price){
        this.companyName = companyName;
        this.symbol = symbol;
        this.price = price;
    }

    public Stock(String companyName, String symbol){
        this.companyName = companyName;
        this.symbol = symbol;
    }

    public Stock(String symbol){
        this.symbol = symbol;
    }

    public String getCompanyName() {return this.companyName;}
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public String getSymbol() {return this.symbol;}
    public void setSymbol(String symbol) {this.symbol = symbol;}

    public double getPrice() {return this.price;}
    public void setPrice(double price) {this.price = price;}
}
