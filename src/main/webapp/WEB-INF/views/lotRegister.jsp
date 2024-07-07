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
	<h2>Register Lot</h2>
    <form:form modelAttribute="lot" method="post" action="${pageContext.request.contextPath}/lot/doregister">
        <table>
        	<tr>
                <td>Expired Date:</td>
                <td><form:input path="expiredDate" type="date"/></td>
            </tr>
            <tr>
                <td>Price:</td>
                <td><form:input path="price"/></td>   
            </tr>
            <tr>
                <td>Date:</td>
                <td><form:input path="date" type="date"/></td>
            </tr>
            <tr>
                <td>UOM:</td>
                <td>
                	<form:select path="uom">
					<form:option value="NONE" label="Select"/>
					<form:options items="${UOMList}" />								
					</form:select>
				</td>
            </tr>
            <tr>
                <td>Quantity:</td>
                <td><form:input path="quantity"/></td>
            </tr>
            <tr>
                <td>Product:</td>
                <td>
                    <form:select path="productId">
                        <form:options items="${products}" itemValue="id" itemLabel="name"/>
                    </form:select>
                </td>
            </tr>
            
           
            
            
            
            
            
            
            <tr>
                <td colspan="2"><input type="submit" value="Register"/></td>
            </tr>
        </table>
    </form:form>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
</body>
</html>