package com.es.core.cart.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartAccessor;
import com.es.core.cart.CartItem;
import com.es.core.model.exception.OutOfStockException;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.entity.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ContextConfiguration("classpath*:/applicationContext-testService.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private PhoneDao phoneDao;
    @Spy
    private CartAccessor cart;
    @InjectMocks
    private HttpSessionCartService cartService;
    private final static Long PHONE_ID_1 = 1003L;
    private final static Long PHONE_ID_2 = 1001L;
    private final static Long STOCK_QUANTITY = 2L;
    private final static Long QUANTITY_TO_ADD_TO_CART = 1L;
    private final static Long QUANTITY_TO_UPDATE_CART = 2L;
    private final static Long QUANTITY_OUT_OF_STOCK = 5L;
    private final static Long QUANTITY_IN_CART = 1L;
    private final static BigDecimal PHONE_PRICE = BigDecimal.ONE;
    private final static Phone PHONE_1 = new Phone(PHONE_ID_1, PHONE_PRICE);
    private final static Phone PHONE_2 = new Phone(PHONE_ID_2, PHONE_PRICE);

    @Before
    public void setUp() {
        cart = new Cart();
        ArrayList<CartItem> cartItems = new ArrayList<>(List.of(new CartItem(PHONE_1, QUANTITY_IN_CART)));
        cart.setItems(cartItems);

        MockitoAnnotations.initMocks(this);

        when(phoneDao.get(anyLong())).thenReturn(Optional.of(PHONE_1));
        when(phoneDao.getStockByPhoneId(anyLong())).thenReturn(STOCK_QUANTITY.intValue());
    }

    @Test
    public void testAddSamePhoneToCart() throws OutOfStockException {
        cartService.addPhone(PHONE_ID_1, QUANTITY_TO_ADD_TO_CART);

        boolean isOneCartItemWithMultipleInstances = cart.getItems()
                .stream()
                .anyMatch(cartItem -> cartItem
                        .getPhone()
                        .getId()
                        .equals(PHONE_ID_1)
                        && cartItem.getQuantity() == 2L);

        assertTrue(isOneCartItemWithMultipleInstances);
        assertEquals(1L, cart.getItems().size());
        assertEquals(BigDecimal.valueOf(2), cart.getTotalCost());
        assertEquals(2L, cart.getTotalQuantity().longValue());
    }

    @Test
    public void testAddDifferentPhoneToCart() throws OutOfStockException {
        when(phoneDao.get(PHONE_ID_2)).thenReturn(Optional.of(PHONE_2));
        cartService.addPhone(PHONE_ID_2, QUANTITY_TO_ADD_TO_CART);

        boolean isTwoCartItems = cart.getItems()
                .stream()
                .anyMatch(cartItem -> cartItem.getQuantity() == 1L);

        assertTrue(isTwoCartItems);
        assertEquals(2L, cart.getItems().size());
        assertEquals(2L, cart.getTotalQuantity().longValue());
        assertEquals(BigDecimal.valueOf(2), cart.getTotalCost());
    }

    @Test(expected = OutOfStockException.class)
    public void whenAddPhoneQuantityOutOfStockThenThrowOutOfStockException() throws OutOfStockException {
        cartService.addPhone(PHONE_ID_1, QUANTITY_OUT_OF_STOCK);
    }

    @Test
    public void testDeleteCartItem() {
        cartService.remove(PHONE_ID_1);

        assertTrue(cart.getItems().isEmpty());
        assertEquals(0L, cart.getTotalQuantity().longValue());
        assertEquals(BigDecimal.ZERO, cart.getTotalCost());
    }

    @Test
    public void testUpdateCart() {
        Map<Long, Long> updateParameterMap = Map.of(PHONE_ID_1, QUANTITY_TO_UPDATE_CART);

        cartService.update(updateParameterMap);

        boolean isOneCartItemWithMultipleInstances = cart.getItems()
                .stream()
                .anyMatch(cartItem -> cartItem
                        .getPhone()
                        .getId()
                        .equals(PHONE_ID_1)
                        && cartItem.getQuantity() == 2L);

        assertTrue(isOneCartItemWithMultipleInstances);
        assertEquals(1L, cart.getItems().size());
        assertEquals(2L, cart.getTotalQuantity().longValue());
        assertEquals(BigDecimal.valueOf(2), cart.getTotalCost());
    }
}
