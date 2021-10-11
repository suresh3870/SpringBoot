package com.surabi.restaurants.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import java.util.Objects;


public class BillDetailsDTO {
    private int BILL_ID;
    private String USERNAME;
    private String ITEM;
    private int QTY;
    private int PRICE;
    private int ITEM_TOTALPRICE;
    private double BILL_AMOUNT;

    public BillDetailsDTO() {
    }

    public BillDetailsDTO(int BILL_ID, String USERNAME, String ITEM, int QTY, int PRICE, int ITEM_TOTALPRICE, double BILL_AMOUNT) {
        this.BILL_ID = BILL_ID;
        this.USERNAME = USERNAME;
        this.ITEM = ITEM;
        this.QTY = QTY;
        this.PRICE = PRICE;
        this.ITEM_TOTALPRICE = ITEM_TOTALPRICE;
        this.BILL_AMOUNT = BILL_AMOUNT;
    }

    public int getBILL_ID() {
        return BILL_ID;
    }

    public void setBILL_ID(int BILL_ID) {
        this.BILL_ID = BILL_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getITEM() {
        return ITEM;
    }

    public void setITEM(String ITEM) {
        this.ITEM = ITEM;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

    public int getPRICE() {
        return PRICE;
    }

    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    public int getITEM_TOTALPRICE() {
        return ITEM_TOTALPRICE;
    }

    public void setITEM_TOTALPRICE(int ITEM_TOTALPRICE) {
        this.ITEM_TOTALPRICE = ITEM_TOTALPRICE;
    }

    public double getBILL_AMOUNT() {
        return BILL_AMOUNT;
    }

    public void setBILL_AMOUNT(double BILL_AMOUNT) {
        this.BILL_AMOUNT = BILL_AMOUNT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDetailsDTO that = (BillDetailsDTO) o;
        return BILL_ID == that.BILL_ID && QTY == that.QTY && PRICE == that.PRICE && ITEM_TOTALPRICE == that.ITEM_TOTALPRICE && Double.compare(that.BILL_AMOUNT, BILL_AMOUNT) == 0 && Objects.equals(USERNAME, that.USERNAME) && Objects.equals(ITEM, that.ITEM);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BILL_ID, USERNAME, ITEM, QTY, PRICE, ITEM_TOTALPRICE, BILL_AMOUNT);
    }
}
