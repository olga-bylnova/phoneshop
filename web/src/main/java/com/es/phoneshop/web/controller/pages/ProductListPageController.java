package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.entity.SortField;
import com.es.core.model.phone.entity.SortOrder;
import org.springframework.beans.factory.annotation.Value;
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
    private CartService cartService;
    @Value("${page.limit}")
    private int limit;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(required = false) SortOrder order,
                                  @RequestParam(required = false) SortField sort,
                                  @RequestParam(required = false) String query,
                                  @RequestParam(required = false, defaultValue = "1") Integer page) {
        int offset = limit * (page - 1);

        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("prevPage", page == 1 ? page : page - 1);
        model.addAttribute("currentPage", page);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("phones", phoneDao.findAll(offset, limit, sort, order, query));
        return "productList";
    }
}
