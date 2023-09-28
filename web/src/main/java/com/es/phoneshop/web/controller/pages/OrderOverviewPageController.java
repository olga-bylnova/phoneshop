package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.service.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.dao.OrderDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    private CartService cartService;
    @Resource
    private OrderDao orderDao;

    @GetMapping("/{id}")
    public String getOrderReview(@PathVariable String id,
                                 Model model) {
        Optional<Order> orderOptional = orderDao.getOrderBySecureId(id);

        return orderOptional.map(order -> {
            model.addAttribute("order", order);
            model.addAttribute("cart", cartService.getCart());
            return "orderOverview";
        }).orElseGet(() -> {
            model.addAttribute("id", id);
            return "/error/orderNotFound";
        });
    }
}
