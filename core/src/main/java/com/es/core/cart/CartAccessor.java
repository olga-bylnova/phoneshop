package com.es.core.cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartAccessor {
    void setItems(List<CartItem> items);

    Long getTotalQuantity();

    void setTotalQuantity(Long totalQuantity);

    BigDecimal getTotalCost();

    void setTotalCost(BigDecimal totalCost);

    List<CartItem> getItems();
}
