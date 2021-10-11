package com.surabi.restaurants.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import java.util.List;



public class BillOrderDetailsDTO {
    private int BILL_ID;
    private String USERNAME;
    private double BILL_AMOUNT;
    private List<BillDetailDTO> billDetailDTO;

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

    public double getBILL_AMOUNT() {
        return BILL_AMOUNT;
    }

    public void setBILL_AMOUNT(double BILL_AMOUNT) {
        this.BILL_AMOUNT = BILL_AMOUNT;
    }

    public List<BillDetailDTO> getBillDetailDTO() {
        return billDetailDTO;
    }

    public void setBillDetailDTO(List<BillDetailDTO> billDetailDTO) {
        this.billDetailDTO = billDetailDTO;
    }

    public BillOrderDetailsDTO(int BILL_ID, String USERNAME, double BILL_AMOUNT, List<BillDetailDTO> billDetailDTO) {
        this.BILL_ID = BILL_ID;
        this.USERNAME = USERNAME;
        this.BILL_AMOUNT = BILL_AMOUNT;
        this.billDetailDTO = billDetailDTO;
    }

    public BillOrderDetailsDTO() {
    }

    public BillOrderDetailsDTO(String USERNAME) {
        this.USERNAME = USERNAME;
    }
}