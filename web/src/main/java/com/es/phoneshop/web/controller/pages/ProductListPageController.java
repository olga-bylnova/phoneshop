package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.entity.SortField;
import com.es.core.model.phone.entity.SortOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private Cart cart;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(required = false) SortOrder order,
                                  @RequestParam(required = false) SortField sort,
                                  @RequestParam(required = false) String query) {
        model.addAttribute(cart);
        model.addAttribute("phones", phoneDao.findAll(0, 10, sort, order, query));
        return "productList";
    }
}
