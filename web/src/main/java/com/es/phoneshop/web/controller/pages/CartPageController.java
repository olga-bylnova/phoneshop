package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartAccessor;
import com.es.core.cart.service.CartService;
import com.es.core.cart.dto.CartItemDto;
import com.es.core.cart.dto.CartUpdateDto;
import com.es.core.cart.dto.UpdateErrorDto;
import com.es.phoneshop.web.controller.validator.QuantityValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

import static com.es.core.model.phone.util.StringUtil.SUCCESSFULLY_UPDATED_CART_MESSAGE;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;
    @Resource
    private QuantityValidator validator;

    @InitBinder("cartUpdateDto")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        CartAccessor cart = cartService.getCart();

        model.addAttribute("cart", cart);
        model.addAttribute("cartUpdateDto", new CartUpdateDto());

        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(@ModelAttribute CartUpdateDto cartUpdateDto,
                             BindingResult bindingResult,
                             Model model) {
        validator.validate(cartUpdateDto, bindingResult);

        Map<Long, UpdateErrorDto> errors = validator.getErrors();

        if (errors.isEmpty()) {
            Map<Long, Long> resultMap = cartUpdateDto.getItemDtos().stream()
                    .collect(Collectors.toMap(CartItemDto::getPhoneId, CartItemDto::getQuantity));

            cartService.update(resultMap);

            model.addAttribute("success", SUCCESSFULLY_UPDATED_CART_MESSAGE);
        }
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("errors", errors);
        return "cart";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String deleteItem(@RequestParam Long phoneId) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }
}
