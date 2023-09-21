package com.es.phoneshop.web.controller;

import com.es.core.cart.CartAccessor;
import com.es.core.cart.dto.CartInfoDto;
import com.es.core.cart.dto.CartItemDto;
import com.es.core.cart.CartService;
import com.es.core.model.exception.OutOfStockException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    public static final String WRONG_QUANTITY_VALUE_MESSAGE = "Wrong quantity value";
    public static final String OUT_STOCK_MESSAGE = "Out of stock, max available ";
    public static final String SUCCESSFULLY_ADDED_TO_CART_MESSAGE = "Successfully added to cart";
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addPhone(@Valid @RequestBody CartItemDto cartItemDto,
                                           BindingResult result) throws JsonProcessingException {
        if (result.hasErrors()) {
            return getJsonResponse(WRONG_QUANTITY_VALUE_MESSAGE, true);
        }

        try {
            cartService.addPhone(cartItemDto.getPhoneId().longValue(), cartItemDto.getQuantity().longValue());
        } catch (OutOfStockException e) {
            return getJsonResponse(OUT_STOCK_MESSAGE + e.getStockAvailable(), true);
        }

        return getJsonResponse(SUCCESSFULLY_ADDED_TO_CART_MESSAGE, false);
    }

    private ResponseEntity<String> getJsonResponse(String message, boolean isError) throws JsonProcessingException {
        CartAccessor cart = cartService.getCart();

        CartInfoDto cartInfoDto = new CartInfoDto(message, isError);
        cartInfoDto.setTotalCost(cart.getTotalCost());
        cartInfoDto.setTotalQuantity(cart.getTotalQuantity());

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.writeValueAsString(cartInfoDto), HttpStatus.OK);
    }
}
