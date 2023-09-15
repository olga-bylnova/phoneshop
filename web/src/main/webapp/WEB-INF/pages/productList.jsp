<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>
<link rel="stylesheet" href="styles/main.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<p id="errors" class="error">
</p>
<p id="success" class="success">
</p>

<div class="d-flex justify-content-between" id="header">
    <tags:cartDisplay cart="${cart}"/>
    <form>
        <input class="form-control" placeholder="Search" name="query" value="${param.query}">
        <button class="btn btn-light">Search</button>
    </form>
</div>
<table class="table table-bordered table-striped">
    <thead>
    <tr class="table-secondary">
        <td>Image</td>
        <td>
            <div class="d-flex justify-content-between">
                Brand
                <div>
                    <tags:sortLinkAsc sort="BRAND" order="ASC"/>
                    <tags:sortLinkDesc sort="BRAND" order="DESC"/>
                </div>
            </div>
        </td>
        <td>
            <div class="d-flex justify-content-between">
                Model
                <div>
                    <tags:sortLinkAsc sort="MODEL" order="ASC"/>
                    <tags:sortLinkDesc sort="MODEL" order="DESC"/>
                </div>
            </div>
        </td>
        <td>Color</td>
        <td>
            <div class="d-flex justify-content-between">Display size
                <div>
                    <tags:sortLinkAsc sort="DISPLAY_SIZE" order="ASC"/>
                    <tags:sortLinkDesc sort="DISPLAY_SIZE" order="DESC"/>
                </div>
            </div>
        </td>
        <td>
            <div class="d-flex justify-content-between">Price
                <div>
                    <tags:sortLinkAsc sort="PRICE" order="ASC"/>
                    <tags:sortLinkDesc sort="PRICE" order="DESC"/>
                </div>
            </div>
        </td>
        <td>Quantity</td>
        <td>Action</td>
    </tr>
    </thead>
    <c:forEach var="phone" items="${phones}">
        <tr>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            </td>
            <td>${phone.brand}</td>
            <td>${phone.model}</td>
            <td>
                <c:forEach var="color" items="${phone.colors}">
                    <p>${color.code}</p>
                </c:forEach>
            </td>
            <td>${phone.displaySizeInches}''</td>
            <td><fmt:formatNumber value="${phone.price}" type="currency"
                                  currencySymbol="$"/></td>
            <td>
                <div id="addToCart${phone.id}">
                    <input class="form-control"
                           name="quantity"
                           value="1">
                    <input name="productId" value="${phone.id}" type="hidden"/>
                    <p class="error" id="error${phone.id}"></p>
                </div>
            </td>
            <td>
                <button class="btn btn-light" onclick="handleButtonClick(${phone.id})">Add to cart</button>
            </td>
        </tr>
    </c:forEach>
</table>
<tags:pagination/>

<script src="scripts/addToCart.js" type="text/javascript"></script>