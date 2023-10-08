package com.es.core.order;

import com.es.core.cart.CartAccessor;
import com.es.core.model.exception.OutOfStockException;
import com.es.core.model.order.Order;

public interface OrderService {
    Order createOrder(CartAccessor cart);
    void placeOrder(Order order) throws OutOfStockException;
}
