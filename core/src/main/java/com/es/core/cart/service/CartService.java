package com.es.core.cart.service;

import com.es.core.cart.CartAccessor;
import com.es.core.model.exception.OutOfStockException;
import com.es.core.model.phone.entity.Phone;

import java.util.Map;

public interface CartService {

    CartAccessor getCart();

    void addPhone(Long phoneId, Long quantity) throws OutOfStockException;

    /**
     * @param items
     * key: {@link Phone#id}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId);
    void checkStockAvailable(Long phoneId, Long quantity) throws OutOfStockException;
    void clearCart();
}
