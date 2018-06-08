package com.bongtran.moneysaving.models;

public class Currency {
    private String name;
    private String shortName;
    private String symbol;
    private double dollarRate;

    public Currency() {

    }

    public Currency(String name, String shortName, String symbol, double dollarRate) {
        this.dollarRate = dollarRate;
        this.name = name;
        this.shortName = shortName;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getDollarRate() {
        return dollarRate;
    }

    public void setDollarRate(double dollarRate) {
        this.dollarRate = dollarRate;
    }
}
