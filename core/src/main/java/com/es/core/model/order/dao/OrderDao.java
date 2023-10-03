package com.es.core.model.order.dao;

import com.es.core.model.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    void save(Order order);

    Optional<Order> getOrderBySecureId(String secureId);
    List<Order> getOrders();
}
