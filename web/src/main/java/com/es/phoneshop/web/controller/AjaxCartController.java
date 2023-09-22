package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItemDto;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.validator.QuantityValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public ResponseEntity<String> addPhone(@Valid @RequestBody CartItemDto cartItemDto,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Error adding to cart", HttpStatus.BAD_REQUEST);
        }
        cartService.addPhone(cartItemDto.getPhoneId().longValue(), cartItemDto.getQuantity().longValue());
        return new ResponseEntity<>("Successfully added to cart", HttpStatus.OK);
    }
}
