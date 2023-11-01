package com.es.phoneshop.web.controller.validator;

import com.es.core.cart.service.CartService;
import com.es.core.model.exception.OutOfStockException;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.entity.Phone;
import com.es.core.quickorderentry.QuickOrderEntry;
import com.es.core.quickorderentry.QuickOrderEntryItem;
import com.es.core.quickorderentry.dto.InputErrorDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.*;

import static com.es.core.model.phone.util.StringUtilInformationMessage.OUT_OF_STOCK_MESSAGE;

@Component
public class QuickOrderEntryValidator implements Validator {
    @Resource
    private CartService cartService;
    @Resource
    private PhoneDao phoneDao;
    private Map<Integer, InputErrorDto> errorMessages;
    private List<String> successMessages;


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(QuickOrderEntry.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        errorMessages = new HashMap<>();
        successMessages = new ArrayList<>();
        QuickOrderEntry quickOrderEntry = (QuickOrderEntry) o;
        int index = 0;
        for (QuickOrderEntryItem cartItem : quickOrderEntry.getCartItems()) {
            InputErrorDto errorMessage = new InputErrorDto();
            if (cartItem.getQuantity().isBlank() && cartItem.getModelName().isBlank()) {
                index++;
                continue;
            }
            long quantity;
            boolean isQuantityValid = validateQuantity(cartItem, errorMessage);

            if (cartItem.getModelName().isBlank()) {
                errorMessage.setModelNameErrorMessage("Model name is empty");
            }
            Optional<Phone> phone = phoneDao.getByModel(cartItem.getModelName());
            if (phone.isEmpty()) {
                errorMessage.setModelNameErrorMessage("Phone not found");
            } else {
                if (isQuantityValid) {
                    quantity = Long.parseLong(cartItem.getQuantity());
                    try {
                        cartService.addPhone(phone.get(), quantity);

                        successMessages.add(cartItem.getModelName() + " phone added to cart successfully");
                        quickOrderEntry.getCartItems().set(index, null);
                    } catch (OutOfStockException e) {
                        errorMessage.setQuantityErrorMessage(OUT_OF_STOCK_MESSAGE + e.getStockAvailable());
                    }
                }
            }

            if (errorMessage.getQuantityErrorMessage() != null || errorMessage.getModelNameErrorMessage() != null) {
                errorMessages.put(index, errorMessage);
            }
            index++;
        }
    }

    public Map<Integer, InputErrorDto> getErrorMessages() {
        return errorMessages;
    }

    public List<String> getSuccessMessages() {
        return successMessages;
    }

    private boolean validateQuantity(QuickOrderEntryItem cartItem, InputErrorDto errorMessage) {
        try {
            long quantity = Long.parseLong(cartItem.getQuantity());
            if (quantity <= 0) {
                errorMessage.setQuantityErrorMessage("Quantity must be a positive value");
                return false;
            }
        } catch (NumberFormatException e) {
            errorMessage.setQuantityErrorMessage("Not a number");
            return false;
        }

        return true;
    }
}
