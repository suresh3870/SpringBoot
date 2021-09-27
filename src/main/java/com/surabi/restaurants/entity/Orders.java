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

public class Orders {

    @Id
    @GeneratedValue (strategy= GenerationType.SEQUENCE, generator="ordSeqGen")
    @SequenceGenerator(name = "ordSeqGen", sequenceName = "ORD_SEQ_GEN")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer OrderId;

    @Temporal(TemporalType.TIMESTAMP)
    public Date OrderDate;

    @ManyToOne
    @JoinColumn(name="username", nullable=false)
    private User user;


}
