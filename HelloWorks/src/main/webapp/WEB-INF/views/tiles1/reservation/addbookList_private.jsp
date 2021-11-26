<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

	th {background-color: #DDD;}
	
	.subjectStyle {font-weight: bold;
	               color: navy;
	               cursor: pointer;}

</style>


<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div style="margin: 100px 0 800px 243px; padding-left: 3%;">

<div style="display: flex;">
<div style="margin: auto; padding-left: 3%;">

	<h2 style="margin-bottom: 30px;">개인연락처</h2>
	
	<table style="width: 1024px" class="table table-bordered">
		<thead>
		<tr>
			<th style="width: 70px; text-align: center;">이름</th>
			<th style="width: 100px;text-align: center;">이메일</th>
			<th style="width: 70px; text-align: center;">전화번호</th>
			<th style="width: 100px; text-align: center;">회사</th>
			<th style="width: 70px; text-align: center;">태그</th>
		</tr>	
		</thead>
		
		<tbody>
		<c:forEach var="addbookvo" items="${requestScope.addbookList_private}">
			<tr>
				<td align="center">${addbookvo.name}</td>
				<td align="left"> ${addbookvo.email}</td>
				<td align="center">${addbookvo.phonenum}</td>
				<td align="center">${addbookvo.company}</td>
				<td align="center">${addbookvo.tag}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	
	
</div>
</div>	
</div>

<!-- !!!!!PAGE CONTENT END!!!!!! -->


