package com.surabi.restaurants.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


public class BillDetailDTO {

    private String ITEM;
    private int QTY;
    private int PRICE;
    private int ITEM_TOTALPRICE;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDetailDTO that = (BillDetailDTO) o;
        return QTY == that.QTY && PRICE == that.PRICE && ITEM_TOTALPRICE == that.ITEM_TOTALPRICE && Objects.equals(ITEM, that.ITEM);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ITEM, QTY, PRICE, ITEM_TOTALPRICE);
    }
}
