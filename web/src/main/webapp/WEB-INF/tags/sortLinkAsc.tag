<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?sort=${sort}&order=${order}&query=${param.query}&page=${param.page}">
    <c:choose>
        <c:when test="${sort eq param.sort && order eq param.order}">
            <i class="fa fa-caret-up" style="color:black"></i>
        </c:when>
        <c:otherwise>
            <i class="fa fa-caret-up"></i>
        </c:otherwise>
    </c:choose>
</a>