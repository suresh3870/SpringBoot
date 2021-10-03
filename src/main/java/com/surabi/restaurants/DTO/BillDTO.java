package com.surabi.restaurants.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private int BILL_ID;
    private String USERNAME;
    private String BILL_DATE;
    private double BILL_AMOUNT;

}
