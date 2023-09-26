package com.es.core.order;

import com.es.core.cart.CartAccessor;
import com.es.core.cart.service.CartService;
import com.es.core.model.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.order.dao.OrderDao;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import static com.es.core.model.phone.util.StringUtil.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("${delivery.price}")
    private int deliveryPrice;
    @Resource
    private CartService cartService;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private OrderDao orderDao;

    @Override
    public Order createOrder(CartAccessor cart) {
        Order order = new Order();
        Random random = new Random();

        order.setOrderItems(cart.getItems()
                .stream()
                .map(item -> new OrderItem(random.nextLong(),
                        item.getPhone(), order, item.getQuantity()))
                .collect(Collectors.toList()));
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryPrice(BigDecimal.valueOf(deliveryPrice));
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        order.setStatus(OrderStatus.NEW);

        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Long> idList = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> {
            int stockByPhoneId = phoneDao.getStockByPhoneId(orderItem.getPhone().getId());
            if (stockByPhoneId < orderItem.getQuantity()) {
                stringBuilder.append(String.format(OUT_OF_STOCK_MESSAGE_WITH_PHONE_ID, orderItem.getPhone().getId(), stockByPhoneId));

                idList.add(orderItem.getPhone().getId());
            }
        });

        if (!stringBuilder.isEmpty()) {
            idList.forEach(id -> {
                order.getOrderItems().removeIf(item -> item.getPhone().getId().equals(id));
                cartService.remove(id);
            });
            throw new OutOfStockException(stringBuilder.toString());
        }

        orderDao.save(order);
        order.getOrderItems().forEach(orderItem -> phoneDao.updateProductStock(orderItem.getPhone().getId(), orderItem.getQuantity().intValue()));
    }
}
