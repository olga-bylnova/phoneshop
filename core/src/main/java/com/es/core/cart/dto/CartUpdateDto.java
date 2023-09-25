package com.es.core.cart.dto;

import java.util.List;

public class CartUpdateDto {
    private List<CartItemDto> itemDtos;

    public CartUpdateDto(List<CartItemDto> itemDtos) {
        this.itemDtos = itemDtos;
    }

    public CartUpdateDto() {
    }

    public void setItemDtos(List<CartItemDto> itemDtos) {
        this.itemDtos = itemDtos;
    }

    public List<CartItemDto> getItemDtos() {
        return itemDtos;
    }
}
