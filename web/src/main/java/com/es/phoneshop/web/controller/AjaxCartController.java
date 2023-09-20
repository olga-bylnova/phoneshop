package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItemDto;
import com.es.core.cart.CartService;
import com.es.core.model.exception.OutOfStockException;
import com.es.phoneshop.web.validator.QuantityValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;
    @Resource
    private QuantityValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addPhone(@Valid @RequestBody CartItemDto cartItemDto,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Wrong quantity value", HttpStatus.BAD_REQUEST);
        }
        try {
            cartService.addPhone(cartItemDto.getPhoneId().longValue(), cartItemDto.getQuantity().longValue());
        } catch (OutOfStockException e) {
            return new ResponseEntity<>("Out of stock, max available " + e.getStockAvailable(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully added to cart", HttpStatus.OK);
    }
}
