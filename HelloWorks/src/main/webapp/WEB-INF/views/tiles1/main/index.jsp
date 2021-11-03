<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%> 


<c:forEach var ="name" items="${requestScope.nameList}" varStatus="status">
	${name}
</c:forEach>  

<div>확인용</div>