package com.es.core.cart;

import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.entity.Phone;
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
    public void addPhone(Long phoneId, Long quantity) {
        Optional<Phone> phoneOptional = phoneDao.get(phoneId);

        if (phoneOptional.isPresent()) {
            Optional<CartItem> itemOptional = findCartItemForUpdate(cart, phoneId);

            if (itemOptional.isPresent()) {
                CartItem item = itemOptional.get();
                quantity += item.getQuantity();

                item.setQuantity(quantity);
            } else {
                cart.getItems().add(new CartItem(phoneOptional.get(), quantity));
            }
            recalculateCart(cart);
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private Optional<CartItem> findCartItemForUpdate(CartAccessor cart, Long phoneId) {
        List<CartItem> items = cart.getItems();

        return items.stream()
                .filter(item -> phoneId.equals(item.getPhone().getId()))
                .findAny();
    }

    private void recalculateCart(CartAccessor cart) {
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
