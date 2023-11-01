package com.es.core.quickorderentry;

import java.util.List;

public class QuickOrderEntry {
    private List<QuickOrderEntryItem> cartItems;
    public List<QuickOrderEntryItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<QuickOrderEntryItem> cartItems) {
        this.cartItems = cartItems;
    }
}
