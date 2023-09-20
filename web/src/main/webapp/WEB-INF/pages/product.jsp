<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:master>
    <p id="errors" class="error"></p>
    <p id="success" class="success"></p>
    <a href="${pageContext.request.contextPath}/productList">
        <button class="btn btn-light">Back to product list
        </button>
    </a>
    <div class="d-flex" id="phoneInfo">
        <div id="phoneDescription">
            <p>${phone.model}</p>
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            <p>${phone.description}</p>
            <div>
                <p>Price: <fmt:formatNumber value="${phone.price}" type="currency"
                                            currencySymbol="$"/>
                </p>

                <div id="addToCart${phone.id}">
                    <input class="form-control"
                           name="quantity"
                           value="1">
                    <input name="productId" value="${phone.id}" type="hidden"/>
                    <p class="error" id="error${phone.id}"></p>
                    <button class="btn btn-light" onclick="handleButtonClick(${phone.id})">Add to cart</button>
                </div>
            </div>
        </div>
        <div id="phoneParameters">
            <span>Display</span>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <td>Size</td>
                    <td>${phone.displaySizeInches}''</td>
                </tr>
                <tr>
                    <td>Resolution</td>
                    <td>${phone.displayResolution}</td>
                </tr>
                <tr>
                    <td>Technology</td>
                    <td>${phone.displayTechnology}</td>
                </tr>
                <tr>
                    <td>Pixel density</td>
                    <td>${phone.pixelDensity}</td>
                </tr>
                </tbody>
            </table>
            <span>Dimensions & weight</span>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <td>Length</td>
                    <td>${phone.lengthMm}mm</td>
                </tr>
                <tr>
                    <td>Width</td>
                    <td>${phone.widthMm}mm</td>
                </tr>
                <tr>
                    <td>Color</td>
                    <td>${phone.colors.toArray()[0].code}</td>
                </tr>
                <tr>
                    <td>Weight</td>
                    <td>${phone.weightGr}</td>
                </tr>
                </tbody>
            </table>
            <span>Camera</span>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <td>Front</td>
                    <td>${phone.frontCameraMegapixels} megapixels</td>
                </tr>
                <tr>
                    <td>Back</td>
                    <td>${phone.backCameraMegapixels} megapixels</td>
                </tr>
                </tbody>
            </table>
            <span>Battery</span>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <td>Talk time</td>
                    <td>${phone.talkTimeHours} hours</td>
                </tr>
                <tr>
                    <td>Stand by time</td>
                    <td>${phone.standByTimeHours} hours</td>
                </tr>
                <tr>
                    <td>Battery capacity</td>
                    <td>${phone.batteryCapacityMah} mAh</td>
                </tr>
                </tbody>
            </table>
            <span>Other</span>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <td>Color</td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}">
                            <p>${color.code}</p>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>Device type</td>
                    <td>${phone.deviceType}</td>
                </tr>
                <tr>
                    <td>Bluetooth</td>
                    <td>${phone.bluetooth}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

</tags:master>
<script src="${pageContext.request.contextPath}/scripts/addToCart.js" type="text/javascript"></script>
