package com.es.core.order;

import com.es.core.cart.CartAccessor;
import com.es.core.cart.service.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("${delivery.price}")
    private int deliveryPrice;
    @Resource
    private CartService cartService;
    @Override
    public Order createOrder(CartAccessor cart) {
        Order order = new Order();
        Random random = new Random();

        order.setId(random.nextLong());
        order.setOrderItems(cart.getItems()
                .stream()
                .map(item -> new OrderItem(random.nextLong(),
                        item.getPhone(), order, item.getQuantity()))
                .collect(Collectors.toList()));
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryPrice(BigDecimal.valueOf(deliveryPrice));
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));

        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        throw new UnsupportedOperationException("TODO");
    }
}
