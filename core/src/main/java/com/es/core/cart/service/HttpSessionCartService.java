package com.es.core.cart.service;

import com.es.core.cart.CartAccessor;
import com.es.core.cart.CartItem;
import com.es.core.model.exception.OutOfStockException;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.entity.Phone;
import com.es.core.quickorderentry.QuickOrderEntry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private CartAccessor cart;
    @Resource
    private PhoneDao phoneDao;

    @Override
    public CartAccessor getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws OutOfStockException {
        checkStockAvailable(phoneId, quantity);
        Optional<Phone> phoneOptional = phoneDao.get(phoneId);

        if (phoneOptional.isPresent()) {
            addPhone(phoneOptional.get(), quantity);
        }
    }

    public void addPhone(Phone phone, Long quantity) throws OutOfStockException {
        checkStockAvailable(phone.getId(), quantity);
        Optional<CartItem> itemOptional = findCartItemForUpdate(phone.getId());
        if (itemOptional.isPresent()) {
            CartItem item = itemOptional.get();
            quantity += item.getQuantity();

            item.setQuantity(quantity);
        } else {
            cart.getItems().add(new CartItem(phone, quantity));
        }
        recalculateCart();
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> entry : items.entrySet()) {
            Long key = entry.getKey();
            Long value = entry.getValue();

            Optional<CartItem> cartItem = cart.getItems()
                    .stream()
                    .filter(item -> item.getPhone().getId().equals(key))
                    .findAny();
            cartItem.ifPresent(item -> item.setQuantity(value));
        }
        recalculateCart();
    }

    @Override
    public void remove(Long phoneId) {
        cart.getItems()
                .removeIf(cartItem -> cartItem.getPhone().getId().equals(phoneId));
        recalculateCart();
    }

    public void checkStockAvailable(Long phoneId, Long quantity) throws OutOfStockException {
        int stockAvailable = phoneDao.getStockByPhoneId(phoneId);
        Optional<CartItem> itemOptional = findCartItemForUpdate(phoneId);

        if (itemOptional.isPresent()) {
            CartItem item = itemOptional.get();
            quantity += item.getQuantity();
        }
        if (stockAvailable < quantity) {
            throw new OutOfStockException(phoneId, quantity.intValue(), stockAvailable);
        }
    }

    public void clearCart() {
        cart.getItems().clear();
        recalculateCart();
    }

    private Optional<CartItem> findCartItemForUpdate(Long phoneId) {
        List<CartItem> items = cart.getItems();

        return items.stream()
                .filter(item -> phoneId.equals(item.getPhone().getId()))
                .findAny();
    }

    private void recalculateCart() {
        cart.setTotalQuantity(cart.getItems()
                .stream()
                .map(CartItem::getQuantity)
                .mapToLong(q -> q)
                .sum());

        BigDecimal totalCost = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            BigDecimal price = item.getPhone().getPrice();
            long quantity = item.getQuantity();

            totalCost = totalCost.add(price.multiply(BigDecimal.valueOf(quantity)));
        }
        cart.setTotalCost(totalCost);
    }
}
