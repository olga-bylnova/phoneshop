<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/main.js" type="text/javascript"></script>
</head>
<body class="product-list">
<main>
    <div id="admin">
        <div id="adminLinks">
            <sec:authorize access="isAuthenticated()">
                <a href="${pageContext.request.contextPath}/admin/orders">Orders</a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <a id="logoutLink" href="${pageContext.request.contextPath}/logout">Logout</a>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <a href="${pageContext.request.contextPath}/login">Login</a>
            </sec:authorize>
        </div>
    </div>
    <jsp:doBody/>
</main>
<p>(c) Expert-soft</p>
</body>
</html>