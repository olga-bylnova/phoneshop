package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartAccessor;
import com.es.core.cart.service.CartService;
import com.es.core.quickorderentry.QuickOrderEntry;
import com.es.phoneshop.web.controller.validator.QuickOrderEntryValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/quickOrderEntry")
public class QuickOrderEntryPageController {
    @Resource
    private CartService cartService;
    @Resource
    private QuickOrderEntryValidator validator;

    @InitBinder("quickOrderEntry")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        CartAccessor cart = cartService.getCart();

        model.addAttribute("cart", cart);
        model.addAttribute("quickOrderEntry", new QuickOrderEntry());

        return "quickOrderEntry";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addItems(@Valid @ModelAttribute("quickOrderEntry") QuickOrderEntry quickOrderEntry,
                           RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errors", validator.getErrorMessages());
        redirectAttributes.addFlashAttribute("quickOrderEntryInput", quickOrderEntry);
        redirectAttributes.addFlashAttribute("success", validator.getSuccessMessages());

        return "redirect:/quickOrderEntry";
    }
}
