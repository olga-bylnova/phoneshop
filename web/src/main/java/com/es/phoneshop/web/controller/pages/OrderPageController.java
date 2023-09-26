package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartAccessor;
import com.es.core.cart.service.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
@SessionAttributes("order")
public class OrderPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        CartAccessor cart = cartService.getCart();
        Order order = orderService.createOrder(cart);

        model.addAttribute(order);
        model.addAttribute("cart", cart);
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid @ModelAttribute("order") Order order,
                             BindingResult bindingResult,
                             Model model) throws OutOfStockException {
        CartAccessor cart = cartService.getCart();
        if (bindingResult.hasErrors()) {
            model.addAttribute("cart", cart);
            return "order";
        } else {
            return "productList";
        }
        //  orderService.placeOrder(null);
    }
}
