<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="nextPage" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="prevPage" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="currentPage" scope="request" type="java.lang.Integer"/>

<div>
    <a href="${pageContext.request.contextPath}/productList?page=${prevPage}&sort=${param.sort}&order=${param.order}&query=${param.query}">
        <i class="fa fa-caret-left"></i></a>
    <c:forEach var="i" begin="0" end="9">
        <tags:page pageNumber="${currentPage+i}" currentPage="${currentPage}"/>
    </c:forEach>
    <a href="${pageContext.request.contextPath}/productList?page=${nextPage}&sort=${param.sort}&order=${param.order}&query=${param.query}">
        <i class="fa fa-caret-right"></i></a>
</div>