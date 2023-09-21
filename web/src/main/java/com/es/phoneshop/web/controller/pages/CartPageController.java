package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartAccessor;
import com.es.core.cart.CartService;
import com.es.core.cart.dto.CartUpdateDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        CartAccessor cart = cartService.getCart();

        model.addAttribute("cart", cart);
        model.addAttribute("cartUpdateDto", new CartUpdateDto());

        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(@ModelAttribute CartUpdateDto cartUpdateDto) {
        cartService.update(null);
        return "redirect:/cart";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String deleteItem(@RequestParam Long phoneId) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }
}
