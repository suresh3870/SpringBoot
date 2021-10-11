package com.surabi.restaurants.DTO;

import lombok.*;

import java.util.Objects;


public class BillDTO {
    private int BILL_ID;
    private String USERNAME;
    private String BILL_DATE;
    private double BILL_AMOUNT;

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

    public String getBILL_DATE() {
        return BILL_DATE;
    }

    public void setBILL_DATE(String BILL_DATE) {
        this.BILL_DATE = BILL_DATE;
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
        BillDTO billDTO = (BillDTO) o;
        return BILL_ID == billDTO.BILL_ID && Double.compare(billDTO.BILL_AMOUNT, BILL_AMOUNT) == 0 && Objects.equals(USERNAME, billDTO.USERNAME) && Objects.equals(BILL_DATE, billDTO.BILL_DATE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BILL_ID, USERNAME, BILL_DATE, BILL_AMOUNT);
    }
}
