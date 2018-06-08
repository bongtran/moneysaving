package com.bongtran.moneysaving.models;

import java.util.Date;

public class Saving {
    private int id;
    private Date savingDate;
    private float amount;
    private Currency currency;
    private String note;

    public Saving(){

    }

    public Saving(float amount, Currency currency, Date savingDate, String note){
        this.amount = amount;
        this.currency = currency;
        this.note = note;
        this.savingDate = savingDate;
    }

    public Date getSavingDate() {
        return savingDate;
    }

    public void setSavingDate(Date savingDate) {
        this.savingDate = savingDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
