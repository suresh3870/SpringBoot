package com.surabi.restaurants.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    private Integer MenuId;


    @Column(name = "Item")
    private String Item;

    @Column(name = "Price")
    private int price;

}
