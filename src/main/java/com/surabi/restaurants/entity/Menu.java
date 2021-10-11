package com.surabi.restaurants.entity;

import com.surabi.restaurants.DTO.BillDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer MenuId;


    @Column(name = "Item")
    private String Item;

    @Column(name = "Price")
    private int price;

    public Menu(String menu_not_found) {
    }

    public Integer getMenuId() {
        return MenuId;
    }

    public void setMenuId(Integer menuId) {
        MenuId = menuId;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Menu(Integer menuId, String item, int price) {
        MenuId = menuId;
        Item = item;
        this.price = price;
    }

    public Menu() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return price == menu.price && Objects.equals(MenuId, menu.MenuId) && Objects.equals(Item, menu.Item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MenuId, Item, price);
    }
}
