package com.surabi.restaurants.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
