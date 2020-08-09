<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<h3>Restaurants</h3>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Name</th>
        <th>Address</th>
        <th>Website</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${restaurants}" var="restaurant">
        <jsp:useBean id="restaurant" type="ru.graduation.model.Restaurant"/>
        <tr>
            <td>${restaurant.name}</td>
            <td>${restaurant.address}</td>
            <td>${restaurant.website}</td>
            <td></td>
            <td></td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>