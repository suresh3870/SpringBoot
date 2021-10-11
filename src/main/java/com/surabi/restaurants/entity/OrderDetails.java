package com.surabi.restaurants.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;



@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer OrderDetailsID;

    @ManyToOne
    @JoinColumn(name="OrderId", nullable=false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name="MenuId", nullable=false)
    private Menu menu;

    private Integer Quantity;

    private double itemTotalprice;

    public Integer getOrderDetailsID() {
        return OrderDetailsID;
    }

    public void setOrderDetailsID(Integer orderDetailsID) {
        OrderDetailsID = orderDetailsID;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public double getItemTotalprice() {
        return itemTotalprice;
    }

    public void setItemTotalprice(double itemTotalprice) {
        this.itemTotalprice = itemTotalprice;
    }

    public OrderDetails(Integer orderDetailsID, Orders orders, Menu menu, Integer quantity, double itemTotalprice) {
        OrderDetailsID = orderDetailsID;
        this.orders = orders;
        this.menu = menu;
        Quantity = quantity;
        this.itemTotalprice = itemTotalprice;
    }

    public OrderDetails() {
    }
}
