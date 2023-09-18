<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="cart" required="true" type="com.es.core.cart.CartAccessor" %>

<button disabled="disabled" class="btn btn-info btn-rounded">
    My cart: ${cart.totalQuantity eq null ? 0 : cart.totalQuantity} items ${cart.totalCost eq null ? 0 : cart.totalCost}$
</button>