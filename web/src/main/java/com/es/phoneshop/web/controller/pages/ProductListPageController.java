package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.entity.SortField;
import com.es.core.model.phone.entity.SortOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.dao.PhoneDao;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(required = false) SortOrder order,
                                  @RequestParam(required = false) SortField sort,
                                  @RequestParam(required = false) String query) {
        model.addAttribute("phones", phoneDao.findAll(0, 10, sort, order, query));
        return "productList";
    }
}
