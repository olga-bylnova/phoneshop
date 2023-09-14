<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="cart" required="true" type="com.es.core.cart.Cart" %>

<button disabled="disabled" class="btn btn-info btn-rounded">
    My cart: ${cart.totalQuantity} items ${cart.totalCost}$
</button>