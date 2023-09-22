package com.es.phoneshop.web.controller.validator;

import com.es.core.cart.service.CartService;
import com.es.core.cart.dto.CartItemDto;
import com.es.core.cart.dto.CartUpdateDto;
import com.es.core.cart.dto.UpdateErrorDto;
import com.es.core.model.exception.OutOfStockException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.es.core.model.phone.util.StringUtil.OUT_OF_STOCK_MESSAGE;
import static com.es.core.model.phone.util.StringUtil.WRONG_QUANTITY_VALUE_MESSAGE;

@Component
public class QuantityValidator implements Validator {
    Map<Long, UpdateErrorDto> idMessageMap;
    @Resource
    private CartService cartService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(CartUpdateDto.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        idMessageMap = new HashMap<>();
        CartUpdateDto cartUpdateDto = (CartUpdateDto) o;
        for (CartItemDto itemDto : cartUpdateDto.getItemDtos()) {
            if (itemDto.getQuantity() == null || itemDto.getQuantity() <= 0) {
                idMessageMap.put(itemDto.getPhoneId(), new UpdateErrorDto(WRONG_QUANTITY_VALUE_MESSAGE, itemDto.getQuantity()));
            } else {
                try {
                    cartService.checkStockAvailable(itemDto.getPhoneId(), itemDto.getQuantity());
                } catch (OutOfStockException e) {
                    idMessageMap.put(itemDto.getPhoneId(), new UpdateErrorDto(OUT_OF_STOCK_MESSAGE + e.getStockAvailable(), itemDto.getQuantity()));
                }
            }
        }
    }

    public Map<Long, UpdateErrorDto> getErrors() {
        return idMessageMap;
    }
}
