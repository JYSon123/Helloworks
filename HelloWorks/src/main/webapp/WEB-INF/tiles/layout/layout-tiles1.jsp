<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- === #24. tiles 를 사용하는 레이아웃1 페이지 만들기 === --%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%
   String ctxPath = request.getContextPath();
%>    
    
<!DOCTYPE html>
<html>
<head>
<title>helloworks</title>
  
<!-- Required meta tags -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> 

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/bootstrap-4.6.0-dist/css/bootstrap.min.css" > 

<!-- Font Awesome 5 Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js" ></script> 
<script type="text/javascript" src="<%= ctxPath%>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script> 

<!-- w3 탬플릿 css -->
<link rel="stylesheet" type="text/css"  href="https://www.w3schools.com/w3css/4/w3.css">

<!-- main 사진  폰트 -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Lato:wght@900&family=Nanum+Gothic:wght@800&display=swap" rel="stylesheet">

<!-- title icon -->
<link rel="icon" href="<%= ctxPath %>/resources/images/favicon.ico" type="image/x-icon" sizes="16x16">

<!-- Font Awesome 5 Icons -->
<script src="https://kit.fontawesome.com/69a29bca1e.js" crossorigin="anonymous"></script>

<!-- Optional JavaScript -->
<script src="<%= ctxPath %>/resources/js/jquery-3.3.1.min.js" type="text/javascript"></script>
<script src="<%= ctxPath %>/resources/bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap-4.6.0-dist/css/bootstrap.min.css" type="text/css">

<!-- Required meta tags -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
 
 <script type="text/javascript" src="<%=request.getContextPath() %>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
 
<%--  ===== 스피너를 사용하기 위해  jquery-ui 사용하기 ===== --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/jquery-ui-1.11.4.custom/jquery-ui.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/jquery-ui-1.11.4.custom/jquery-ui.js"></script>

<%-- *** ajax로 파일을 업로드할때 가장 널리 사용하는 방법 ==> ajaxForm *** --%>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery.form.min.js"></script>

<%--  ===== 스피너를 사용하기 위해  jquery-ui 사용하기 ===== --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/jquery-ui-1.11.4.custom/jquery-ui.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/jquery-ui-1.11.4.custom/jquery-ui.js"></script>

<%-- *** ajax로 파일을 업로드할 때 가장 널리 사용하는 방법 ==> ajaxForm *** --%>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery.form.min.js"></script>
  

<style type="text/css">

   label {
      width: 90%;
   }
   .card {
      border: none;   
   }
   .hidden {
      visibility: hidden;
   }
   
   #home > div > span {
      font-family: 'Lato', sans-serif;
      font-size:55pt; color:white
   }
   
   .my_a{
    font-weight: normal;   
   }
   
   
   .my_a:visited {
     color: inherit;
   }
   
   .my_a:hover {
     color: black !important;
     text-decoration: underline !important;
     font-weight: normal;
   }
   
   .w3-bar-item:hover {
      color: #0070C0 !important;
      background-color: #f5f5f5 !important;
      text-decoration: none !important;
   }

   .p {
      font-size: 13pt;
      margin-top: 10px;
   }

   /* 에니메이션 css 시작 */
   @import url('https://fonts.googleapis.com/css?family=Roboto:500,700');
   
   *,
   *::before,
   *::after {
      box-sizing: border-box;
   }
   
   .body {
      font-family: 'Lato', sans-serif;
      -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
   }
   
   .main {
      display: flex;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
   }
   
   .title-main {
      font-size: 0;
      position: relative;
      overflow: hidden;
      padding-bottom: 0.4rem;
   }
   
   @keyframes up {
      
      100% {
         transform: translateY(0);
      }
      
   }
   
   @keyframes draw {
      
      100% {
         width: 100%;
      }
      
   }
   /* 에니메이션 css 끝*/   
   
</style>

<script type="text/javascript">

   $(document).ready(function(){
   
      /* 버튼 클릭 시 해당 위치로 스크롤 */
       $("#btn1").click(function () {
         var offset = $("#callMenu").offset(); //선택한 태그의 위치를 반환
         $("html").animate({ scrollTop: offset.top }, 400);
       });
      
   
   });
</script>

<!-- 사이드바 function -->    
<script type="text/javascript">
   function w3_open() {
     document.getElementById("mySidebar").style.display = "block";
   }
    
   function w3_close() {
     document.getElementById("mySidebar").style.display = "none";
   }
</script>
</head>

<body>
   <div id="mycontainer">
      <div id="myheader">
         <tiles:insertAttribute name="header" />
      </div>
      
      <div id="mycontent">
         <tiles:insertAttribute name="content" />
      </div>
      
      <div id="myfooter">
         <tiles:insertAttribute name="footer" />
      </div>
   </div>
</body>
</html>    