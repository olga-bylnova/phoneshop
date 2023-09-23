<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="cart" scope="request" type="com.es.core.cart.CartAccessor"/>

<tags:master>
    <c:if test="${not empty errors}">
        <p class="error">
            There was an error updating cart
        </p>
    </c:if>
    <p id="success" class="success">${success}</p>
    <p>Cart</p>

    <div class="d-flex justify-content-between">
        <a href="${pageContext.request.contextPath}/productList">
            <button class="btn btn-light">Back to product list
            </button>
        </a>
        <a href="#" id="orderButton">
            <button class="btn btn-light">Order
            </button>
        </a>
    </div>
    <c:choose>
        <c:when test="${not empty cart.items}">
            <form:form method="post" modelAttribute="cartUpdateDto" id="updateForm">
                <input type="hidden" name="_method" value="PUT"/>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr class="table-secondary">
                        <td>Bran</td>
                        <td>Model</td>
                        <td>Color</td>
                        <td>Display size</td>
                        <td>Price</td>
                        <td>Quantity</td>
                        <td>Action</td>
                    </tr>
                    </thead>

                    <c:forEach var="item" items="${cart.items}" varStatus="i">
                        <tr>
                            <td>${item.phone.brand}</td>
                            <td>${item.phone.model}</td>
                            <td>
                                <c:forEach var="color" items="${item.phone.colors}">
                                    <p>${color.code}</p>
                                </c:forEach>
                            </td>
                            <td>${item.phone.displaySizeInches}''</td>
                            <td><fmt:formatNumber value="${item.phone.price}" type="currency"
                                                  currencySymbol="$"/></td>
                            <td>
                                <div id="updateCart${item.phone.id}">
                                    <c:set var="index" value="${i.index}"/>
                                    <form:hidden path="itemDtos[${index}].phoneId" value="${item.phone.id}"/>
                                    <form:input cssClass="form-control" path="itemDtos[${index}].quantity"
                                                value="${errors[item.phone.id].input != null ? errors[item.phone.id].input : item.quantity}"/>
                                    <p class="error" id="error${item.phone.id}">${errors[item.phone.id].message}</p>
                                </div>
                            </td>
                            <td>
                                <div>
                                    <input type="hidden" name="phoneId" value="${item.phone.id}">
                                    <button type="button" class="btn btn-light" onclick="deleteItem(${item.phone.id})">
                                        Delete
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <input type="submit" class="btn btn-light" value="Update"/>
            </form:form>
        </c:when>
        <c:otherwise>
            <p>Cart is empty</p>
        </c:otherwise>
    </c:choose>
</tags:master>
<script src="${pageContext.request.contextPath}/scripts/manipulateCart.js" type="text/javascript"></script>
