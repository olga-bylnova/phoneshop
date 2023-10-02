package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.service.CartService;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.entity.Phone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;

    @GetMapping(value = "/{id}")
    public String showProductDetails(Model model,
                                     @PathVariable("id") String id) {
        Long phoneId = Long.valueOf(id);
        Optional<Phone> optionalPhone = phoneDao.get(phoneId);

        if (optionalPhone.isPresent()) {
            model.addAttribute("phone", optionalPhone.get());
            model.addAttribute("cart", cartService.getCart());

            return "product";
        } else {
            model.addAttribute("id", id);
            return "/error/productNotFound";
        }
    }
}
