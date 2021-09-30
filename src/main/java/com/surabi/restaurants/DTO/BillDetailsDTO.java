package com.surabi.restaurants.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailsDTO {
    private int BILL_ID;
    private String USERNAME;
    private String ITEM;
    private int QTY;
    private int PRICE;
    private int ITEM_TOTALPRICE;
    private double BILL_AMOUNT;

    public BillDetailsDTO(String s) {
    }
}
