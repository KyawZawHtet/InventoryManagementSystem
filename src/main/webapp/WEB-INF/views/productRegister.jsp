<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Register Product</h2>
    <form:form modelAttribute="product" method="post" action="${pageContext.request.contextPath}/product/doregister">
        
        <form:label path="name">Product Name:</form:label>
        <form:input path="name"/>
        <form:errors path="name" cssClass="error"/>
        <br/>
        
        <form:label path="categoryId">Category:</form:label>
            <form:select path="categoryId">
                <form:options items="${categories}" itemValue="id" itemLabel="name" />
            </form:select>
        <br/>
        <input type="submit" value="Register"/>
    </form:form>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
</body>
</html>