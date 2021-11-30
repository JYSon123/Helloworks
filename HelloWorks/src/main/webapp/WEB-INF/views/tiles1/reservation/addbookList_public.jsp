<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">

	
	.subjectStyle {font-weight: bold;
	               color: navy;
	               cursor: pointer;}


	body {
	  margin: 0;
	}
	
	ul {
	  list-style-type: none;
	  margin: 0;
	  padding: 0;
	  width: 25%;
	  background-color: #eff4fc;
	  position: fixed;
	  height: 100%;
	  overflow: auto;
	}
	
	li a {
	  display: block;
	  color: #000;
	  padding: 8px 16px;
	  text-decoration: none;
	}
	
	li a.active {
	  background-color: #b2cef6;
	  font-weight: bold;
	  color: #056AC9;
	}
	
	li a:hover:not(.active) {
	  background-color: #f6f6f6;
	  color: black;
	}
	
	.add-address-btn{
	display: block;
    position: relative;
    min-height: 44px;
    max-height: 44px;
    width: 100%;
    min-width: 188px;
    font-size: 18px;
    letter-spacing: -.5px;
    font-weight: 500;
    outline: none;
    border-radius: 4px;
    background: #2985db;
    color: #fff;
    -webkit-transition-duration: .3s;
    transition-duration: .3s;
	
	}
	/* modal position(center)*/
        .modal {
          text-align: center;
        }
        @@media screen and (min-width: 768px) {
          .modal:before {
            display: inline-block;
            vertical-align: middle;
            content: " ";
            height: 100%;
          }
        }
        .modal-dialog {
          display: inline-block;
          text-align: left;
          vertical-align: middle;
        }
	
	
	/* div.modal.show {
        text-align: center;
            width: 600px;
	} */
    
    table#modal-table{
    display:flex;
	display: -webkit-box;
	display: -ms-flexbox;
	overflow-x: auto;
	overflow-y: hidden;
    }


</style>

	<script>
	
	$(document).ready(function(){
		
		$("tr.click").click(function() {
			//console.log($("tr")[6]);
			//console.log(document.getElementsByTagName("ttt")[$(this).index()]);
			//console.log(document.getElementsByTagName('tr')[$(this).index()].childNodes[0].nodeValue);
			
			
			//var myname = $(this).parent("td").val();
			//var myemail = $(this).text();
			//var myphone = $(this).parent().text();
			//console.log($(this));
			//console.log($(this).find('td.addbook_name').text());
			//console.log($("tr#ttt")[$(this).index()].find('td.addbook_name'));
			// 박진우2 , 이메일 , 전화번호 , 회사 , 번호 == > 1
			
			//console.log(document.getElementById("names").innerText);
			$("#name").val($(this).find('td.addbook_name').text());
			$("#email").val($(this).find('td.addbook_email').text());
			$("#phonenum").val($(this).find('td.addbook_phonenum').text());
			
			
			
			
			$("button#modalbtn").click();
			
		});
		
		var testname = document.getElementById('name').value
		//console.log(testname);
		
	});
	
	
	
	
	
	

	</script>


<body>

<ul>
  <li><button type = "button" class = "add-address-btn" id = "add-adrees" data-toggle="modal" data-target="#addbookadd" data-dismiss="modal" data-backdrop="static">주소추가</button></li>
  <li class = "dropdown">
  <a href = "javascript:void(0)" class = "dropbtn" onclick = "dropFunction()"> 개인연락처  <i class="fas fa-angle-down"></i></a>
 
  <div class = "dropdown-content" id = "myDropdown"></div>
  	<a href="<%= ctxPath%>/addbookList_private.hello2"> 전체   </a></li>
  	<c:forEach var="addbook_tagvo" items="${requestScope.addbooktag_private}">
  	<li><a style="cursor: pointer;" href="<%= ctxPath %>/addbookList_private.hello2?fk_tag=${addbook_tagvo.tag}"> ${addbook_tagvo.tag}</a></li>
  	</c:forEach>
  
   <li class = "dropdown">
  <a href = "javascript:void(0)" class = "dropbtn" onclick = "dropFunction()"> 공용연락처  <i class="fas fa-angle-down"></i></a>
  
  <li><a href="<%= ctxPath%>/addbookList_public.hello2">전체 </a></li>
  <c:forEach var="addbook_tagvo" items="${requestScope.addbooktag_public}">
  	<li><a style="cursor: pointer;" href="<%= ctxPath %>/addbookList_public.hello2?fk_tag=${addbook_tagvo.tag}">${addbook_tagvo.tag}</a></li>
  	</c:forEach>	
</ul>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div style="margin: 100px 0 800px 243px; padding-left: 3%;">

<div style="display: flex;">
<div style="margin: auto; padding-left: 3%;">

	<h2 style="margin-bottom: 30px;">공용연락처</h2>
	
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
		<c:forEach var="addbookvo" items="${requestScope.addbookList_public}">
			<tr class = "click" id="ttt">
				<td  class = "addbook_name" align="center" style="cursor: pointer;">${addbookvo.name}</td>
				<td  class = "addbook_email" align="center" style="cursor: pointer;">${addbookvo.email}</td>
				<td  class = "addbook_phonenum" align="center" style="cursor: pointer;">${addbookvo.phonenum}</td>
				<td  class = "addbook_company" align="center" style="cursor: pointer;">${addbookvo.company}</td>
				<td  class = "addbook_fk_tag" align="center" style="cursor: pointer;">${addbookvo.fk_tag}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	
	<span style="display: none;"><button type="button" id="modalbtn" data-toggle="modal" data-target="#addbookinfo" data-dismiss="modal" data-backdrop="static"></button></span>
</div>
</div>	
</div>

<!-- !!!!!PAGE CONTENT END!!!!!! -->

<!-- 사용자의 정보를 나타내는 모달창  -->
  <div class="modal" tabindex="-1"  role="dialog" id="addbookinfo">
  <div class="modal-dialog" style="max-width: 100%; height: 50%; width: auto; display: table;" role="document">
    <div class="modal-content">
      <div class="modal-header">
      	
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<table id="modal-table">
      	<tr> 
      	<th>이름 : &nbsp;</th><td><input type = "text" name = "name" id = "name" value ="" readonly/></td>
      	<th>이메일 : &nbsp;</th><td><input type = "text" name = "email" id = "email" value ="" readonly/></td>
      	<th>전화번호 : &nbsp;</th><td><input type = "text" name = "phonenum" id = "phonenum" value ="" readonly/></td>
      	<th>회사 : &nbsp;</th><td><input type = "text" name = "company" id = "company" value ="" readonly /></td>
      	<th>태그 : &nbsp;</th><td><input type = "text" name = "fk_tag" id = "fk_tag" value ="" readonly/></td>
      	</tr>

      	</table>
      </div>

      <div class="modal-footer">
        <button type="button" class="btn btn-danger myclose" data-dismiss="modal" onClick= "window.location.reload()" >Close</button>
      </div>
    </div>
  </div>
</div>

