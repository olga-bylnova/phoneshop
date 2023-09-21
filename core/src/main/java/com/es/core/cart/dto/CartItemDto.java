package com.es.core.cart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItemDto {
    private Integer phoneId;
    @NotNull(message = "Quantity shouldn't be empty")
    @Min(value = 1, message = "Quantity should be positive")
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public CartItemDto() {
    }

    public CartItemDto(Integer phoneId, Integer quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }
}