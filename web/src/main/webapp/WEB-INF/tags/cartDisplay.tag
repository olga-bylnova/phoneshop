<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="cart" required="true" type="com.es.core.cart.CartAccessor" %>

<div id="cartWrapper">
    <a href="${pageContext.request.contextPath}/cart">
        <button class="btn btn-info btn-rounded" id="cartDisplay">
            My cart: ${cart.totalQuantity eq null ? 0 : cart.totalQuantity}
            items ${cart.totalCost eq null ? 0 : cart.totalCost}$
        </button>
    </a>
</div>