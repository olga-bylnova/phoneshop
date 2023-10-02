package com.es.core.model.order;

import com.es.core.model.phone.entity.Phone;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private Long id;
    private Phone phone;
    private Order order;
    private Long quantity;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(final Phone phone) {
        this.phone = phone;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    public OrderItem(Long id, Phone phone, Order order, Long quantity) {
        this.id = id;
        this.phone = phone;
        this.order = order;
        this.quantity = quantity;
    }

    public OrderItem() {
    }
}
