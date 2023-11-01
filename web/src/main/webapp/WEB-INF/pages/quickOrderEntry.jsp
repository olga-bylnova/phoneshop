<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="cart" scope="request" type="com.es.core.cart.CartAccessor"/>

<tags:master>
    <tags:cartDisplay cart="${cart}"/>
    <c:if test="${not empty errors}">
        <p class="error">
            There was an error
        </p>
    </c:if>
    <c:if test="${not empty success}">
        <c:forEach items="${success}" var="message">
            <p class="success">${message}</p>
        </c:forEach>
    </c:if>

    <p>Quick order entry</p>

    <div class="d-flex justify-content-between">
        <a href="${pageContext.request.contextPath}/productList">
            <button class="btn btn-light">Back to product list
            </button>
        </a>
    </div>
    <form:form method="post" modelAttribute="quickOrderEntry">
        <table>
            <thead>
            <tr>
                <th>Model name</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <c:forEach begin="0" end="10" varStatus="i">
                <c:set var="index" value="${i.index}"/>
                <tr>
                    <td>
                        <form:input path="cartItems[${index}].modelName"
                                    value="${quickOrderEntryInput.cartItems[index].modelName}"/>
                        <span class="error">${errors[index].modelNameErrorMessage}</span>
                    </td>
                    <td>
                        <form:input path="cartItems[${index}].quantity"
                                    value="${quickOrderEntryInput.cartItems[index].quantity}"/>
                        <span class="error">${errors[index].quantityErrorMessage}</span>
                    </td>
                    <td></td>
                </tr>
            </c:forEach>
        </table>
        <button type="submit">Add to cart</button>
    </form:form>
</tags:master>
