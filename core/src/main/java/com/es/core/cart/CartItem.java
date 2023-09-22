package com.es.core.cart;

import com.es.core.model.phone.entity.Phone;

public class CartItem {
    private Phone phone;
    private Long quantity;

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhoneId(Phone phone) {
        this.phone = phone;
    }

    public CartItem() {
    }

    public CartItem(Phone phone, Long quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }
}