<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:master>
    <div class="d-flex justify-content-between">
        <p>Order number: ${order.id}</p>
        <p>Order status: ${order.status}</p>
    </div>
    <table class="table table-bordered table-striped">
        <thead>
        <tr class="table-secondary">
            <td>Brand</td>
            <td>Model</td>
            <td>Color</td>
            <td>Display size</td>
            <td>Quantity</td>
            <td>Price</td>
        </tr>
        </thead>

        <c:forEach var="item" items="${order.orderItems}" varStatus="i">
            <tr>
                <td>${item.phone.brand}</td>
                <td>${item.phone.model}</td>
                <td>
                    <c:forEach var="color" items="${item.phone.colors}">
                        <p>${color.code}</p>
                    </c:forEach>
                </td>
                <td>${item.phone.displaySizeInches}''</td>
                <td>${item.quantity}</td>
                <td><fmt:formatNumber value="${item.phone.price}" type="currency"
                                      currencySymbol="$"/></td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>Subtotal</td>
            <td><fmt:formatNumber value="${order.subtotal}" type="currency"
                                  currencySymbol="$"/></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>Delivery</td>
            <td><fmt:formatNumber value="${order.deliveryPrice}" type="currency"
                                  currencySymbol="$"/></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>TOTAL</td>
            <td><fmt:formatNumber value="${order.totalPrice}" type="currency"
                                  currencySymbol="$"/></td>
        </tr>
    </table>
    <div>
        <table>
            <tr>
                <td>First name</td>
                <td>${order.firstName}</td>
            </tr>
            <tr>
                <td>Last name</td>
                <td>${order.lastName}</td>
            </tr>
            <tr>
                <td>Address</td>
                <td>${order.deliveryAddress}</td>
            </tr>
            <tr>
                <td>Phone</td>
                <td>${order.contactPhoneNo}</td>
            </tr>
            <c:if test="${not empty order.additionalInfo}">
                <tr>
                    <td>Additional info</td>
                    <td>${order.additionalInfo}</td>
                </tr>
            </c:if>
        </table>
    </div>
    <div id="adminButtonPanel">
        <a href="${pageContext.request.contextPath}/admin/orders">
            <button class="btn btn-light">Back</button>
        </a>
        <form method="post">
            <input hidden="hidden" name="status" value="DELIVERED">
            <button class="btn btn-light" type="submit">Delivered</button>
        </form>
        <form method="post">
            <input hidden="hidden" name="status" value="REJECTED">
            <button class="btn btn-light" type="submit">Rejected</button>
        </form>
    </div>
</tags:master>
