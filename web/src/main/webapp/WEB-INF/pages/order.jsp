<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="order" scope="request" type="com.es.core.model.order.Order"/>

<tags:master>
    <p>
        <a href="${pageContext.request.contextPath}/cart">
            <button class="btn btn-light">Back to cart
            </button>
        </a>
    </p>
    <c:if test="${not empty error}">
        <p class="error">
                ${error}
        </p>
    </c:if>
    <c:choose>
        <c:when test="${not empty order.orderItems}">
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
                <form:form method="post" commandName="order" modelAttribute="order" id="orderForm">
                    <table>
                        <tr>
                            <td>First name*</td>
                            <td><form:input path="firstName" placeholder="First name"/>
                                <form:errors path="firstName" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Last name*</td>
                            <td><form:input path="lastName" placeholder="Last name"/>
                                <form:errors path="lastName" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Address*</td>
                            <td><form:input path="deliveryAddress" placeholder="Address"/>
                                <form:errors path="deliveryAddress" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Phone*</td>
                            <td><form:input path="contactPhoneNo" placeholder="Phone: +xxxxxxx"/>
                                <form:errors path="contactPhoneNo" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <textarea id="addInfo" placeholder="Additional information"></textarea>
                            </td>
                        </tr>
                    </table>
                </form:form>
            </div>
            <input id="formButton" type="submit" class="btn btn-light" value="Order"/>
        </c:when>
        <c:otherwise>
            <p>Your order is empty</p>
        </c:otherwise>
    </c:choose>
</tags:master>
<script src="${pageContext.request.contextPath}/scripts/manipulateOrder.js" type="text/javascript"></script>
