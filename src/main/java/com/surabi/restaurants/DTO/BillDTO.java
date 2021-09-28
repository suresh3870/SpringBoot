package com.surabi.restaurants.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private int BILL_ID;
    private int ORDER_ID;
    private String USERNAME;
    private String ORDER_DATE;
    private String ITEM;
    private int QTY;
    private int PRICE;
    private int ITEM_TOTALPRICE;
    private double BILL_AMOUNT;
    private String BILL_DATE;
}
