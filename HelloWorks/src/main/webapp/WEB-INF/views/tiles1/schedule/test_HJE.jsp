<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class= "cotainer" >
<c:forEach var ="name" items="${requestScope.nameList}" varStatus="status">
	<span style="color: black; font-weight: bold;">${status.index }.${name}</span>
</c:forEach>  
</div>