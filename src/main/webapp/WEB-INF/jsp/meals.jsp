<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<h3>Meals</h3>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Name</th>
        <th>Price(in $)</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.graduation.model.Meal"/>
        <tr>
            <td>${meal.name}</td>
            <td>${meal.price}</td>
            <td></td>
            <td></td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>