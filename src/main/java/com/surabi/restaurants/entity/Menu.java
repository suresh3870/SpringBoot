package com.surabi.restaurants.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
