<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:master>
    <c:choose>
        <c:when test="${not empty orders}">
            <span>Orders</span>
            <table class="table table-bordered table-striped">
                <thead>
                <tr class="table-secondary">
                    <td>Order number</td>
                    <td>Customer</td>
                    <td>Phone</td>
                    <td>Address</td>
                    <td>Date</td>
                    <td>Total price</td>
                    <td>Status</td>
                </tr>
                </thead>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/admin/orders/${order.id}">${order.id}</a></td>
                        <td>${order.firstName.concat(" ").concat(order.lastName)}</td>
                        <td>${order.contactPhoneNo}</td>
                        <td>${order.deliveryAddress}</td>
                        <td><fmt:formatDate value="${order.orderDate}" pattern="dd.MM.yyyy HH:mm"/></td>
                        <td><fmt:formatNumber value="${order.totalPrice}" type="currency"
                                              currencySymbol="$"/></td>
                        <td>${order.status}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p>Order list is empty</p>
        </c:otherwise>
    </c:choose>
</tags:master>
