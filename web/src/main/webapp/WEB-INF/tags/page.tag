<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageNumber" required="true" %>
<%@ attribute name="currentPage" required="true" %>

<a class="link-offset-2 link-offset-3-hover link-underline link-underline-opacity-0 link-underline-opacity-75-hover"
   href="${pageContext.request.contextPath}/productList?page=${pageNumber}&sort=${param.sort}&order=${param.order}&query=${param.query}">
    <c:choose>
        <c:when test="${currentPage eq pageNumber}">
            <span class="font-weight-bold">${pageNumber}</span>
        </c:when>
        <c:otherwise>
            ${pageNumber}
        </c:otherwise>
    </c:choose>
</a>