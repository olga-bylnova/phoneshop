package com.es.core.order;

import com.es.core.cart.CartAccessor;
import com.es.core.cart.service.CartService;
import com.es.core.model.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.order.dao.OrderDao;
import com.es.core.model.phone.dao.PhoneDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.es.core.model.phone.util.StringUtilInformationMessage.OUT_OF_STOCK_MESSAGE_WITH_PHONE_ID;

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

        order.setOrderItems(cart.getItems()
                .stream()
                .map(item -> new OrderItem(item.getPhone(), order, item.getQuantity()))
                .collect(Collectors.toList()));
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryPrice(BigDecimal.valueOf(deliveryPrice));
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        order.setStatus(OrderStatus.NEW);
        order.setOrderDate(new Date());

        return order;
    }

    @Override
    @Transactional
    public void placeOrder(Order order) throws OutOfStockException {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Long> idList = new ArrayList<>();

        order.getOrderItems().forEach(orderItem -> {
            int stockByPhoneId = phoneDao.getStockByPhoneId(orderItem.getPhone().getId());
            if (stockByPhoneId < orderItem.getQuantity()) {
                stringBuilder.append(String.format(OUT_OF_STOCK_MESSAGE_WITH_PHONE_ID, orderItem.getPhone().getId()));

                idList.add(orderItem.getPhone().getId());
            }
        });

        if (!stringBuilder.isEmpty()) {
            idList.forEach(id -> {
                order.getOrderItems().removeIf(item -> item.getPhone().getId().equals(id));
                cartService.remove(id);
            });
            recalculateOrder(order);
            throw new OutOfStockException(stringBuilder.toString());
        }

        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
        order.getOrderItems().forEach(orderItem -> phoneDao.updateProductStock(orderItem.getPhone().getId(), orderItem.getQuantity().intValue()));
    }

    private void recalculateOrder(Order order) {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (OrderItem item : order.getOrderItems()) {
            BigDecimal price = item.getPhone().getPrice();
            long quantity = item.getQuantity();

            subTotal = subTotal.add(price.multiply(BigDecimal.valueOf(quantity)));
        }
        order.setSubtotal(subTotal);
        order.setTotalPrice(subTotal.add(order.getDeliveryPrice()));
    }
}
