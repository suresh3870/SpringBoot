package com.surabi.restaurants.entity;
import com.surabi.restaurants.DTO.BillDTO;
import com.surabi.restaurants.DTO.BillDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@SqlResultSetMapping(name="BillDTOMapping",
        classes = {
                @ConstructorResult(targetClass = BillDetailsDTO.class,
                        columns = {@ColumnResult(name="BILL_ID", type = Integer.class),
                                @ColumnResult(name="USERNAME", type = String.class),
                                @ColumnResult(name="ITEM", type = String.class),
                                @ColumnResult(name="QTY", type = Integer.class),
                                @ColumnResult(name="PRICE", type = Integer.class),
                                @ColumnResult(name="ITEM_TOTALPRICE", type = Integer.class),
                                @ColumnResult(name="BILL_AMOUNT", type = double.class)
                        }
                )})

@SqlResultSetMapping(name="BillViewMapping",
        classes = {
                @ConstructorResult(targetClass = BillDTO.class,
                        columns = {@ColumnResult(name="BILL_ID", type = Integer.class),
                                @ColumnResult(name="USERNAME", type = String.class),
                                @ColumnResult(name="BILL_DATE", type = String.class),
                                @ColumnResult(name="BILL_AMOUNT", type = double.class)
                        }
                )})

@Entity
public class Bill {
    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int billID;

    @OneToOne
    @JoinColumn(name="OrderId", nullable=false)
    private Orders orders;
    private double billAmount;
    @Temporal(TemporalType.TIMESTAMP)
    public Date billDate;

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Bill(int billID, Orders orders, double billAmount, Date billDate) {
        this.billID = billID;
        this.orders = orders;
        this.billAmount = billAmount;
        this.billDate = billDate;
    }

    public Bill() {
    }
}
