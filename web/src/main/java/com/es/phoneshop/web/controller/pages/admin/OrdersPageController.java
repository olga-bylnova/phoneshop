package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.order.dao.OrderDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    @Resource
    private OrderDao orderDao;

    @GetMapping
    public String getOrders(Model model) {
        List<Order> orders = orderDao.getOrders();
        model.addAttribute("orders", orders);

        return "adminOrderList";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model) {
        Optional<Order> orderOptional = orderDao.getOrderById(id);

        if (orderOptional.isPresent()) {
            model.addAttribute("order", orderOptional.get());
            return "adminOrder";
        }

        model.addAttribute("id", id);
        return "error/orderNotFound";
    }

    @PostMapping("/{id}")
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam OrderStatus status) {
        orderDao.updateOrderStatusByOrderId(id, status);

        return "redirect:/admin/orders/" + id;
    }
}
