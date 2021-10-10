package com.vosouq.prototype.user.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`order`")
public class Order {

    @Id
    private Integer id;
    private String owner;
    private int totalPrice;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
