package com.es.core.cart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CartInfoDto {
    @JsonProperty
    @JsonInclude()
    private Long totalQuantity;
    @JsonProperty
    private BigDecimal totalCost;
    @JsonProperty
    private String message;
    @JsonProperty
    private boolean isError;

    public CartInfoDto(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    public CartInfoDto() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity == null ? 0L : totalQuantity;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost == null ? BigDecimal.ZERO : totalCost;
    }
}
