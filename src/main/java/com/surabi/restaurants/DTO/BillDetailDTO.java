package com.surabi.restaurants.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailDTO {

    private String ITEM;
    private int QTY;
    private int PRICE;
    private int ITEM_TOTALPRICE;
}
