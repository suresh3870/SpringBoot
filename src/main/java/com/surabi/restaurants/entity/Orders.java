package com.surabi.restaurants.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;



@Entity
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

    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderId) {
        OrderId = orderId;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Orders(Integer orderId, Date orderDate, User user) {
        OrderId = orderId;
        OrderDate = orderDate;
        this.user = user;
    }

    public Orders() {
    }
}
