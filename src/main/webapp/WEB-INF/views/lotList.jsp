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
	
	<h2>List of Lots</h2>
	<a href="${pageContext.request.contextPath}/lot/register">
        <button>Create New Lot</button>
    </a>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Product Name</th>
            <th>Lot No</th>
            <th>Quantity</th>
            <th>UOM</th>
            <th>Date</th>
            <th>Expired Date</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${lotList}" var="lot">
            <tr>
            	<td>${lot.id}</td>
                <td>${lot.productName}</td>
                <td>${lot.lotNumber}</td>
                <td>${lot.quantity}</td>
                <td>${lot.uom}</td>
                <td>${lot.date}</td>
                <td>${lot.expiredDate}</td>
                <td>${lot.price}</td>
                
                <td>
                    <a href="${pageContext.request.contextPath}/lot/editlot/${lot.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/lot/deletelot/${lot.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>