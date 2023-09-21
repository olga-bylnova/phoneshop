package com.es.core.cart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItemDto {
    private Long phoneId;
    @NotNull(message = "Quantity shouldn't be empty")
    @Min(value = 1, message = "Quantity should be positive")
    private Long quantity;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public CartItemDto() {
    }

    public CartItemDto(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }
}