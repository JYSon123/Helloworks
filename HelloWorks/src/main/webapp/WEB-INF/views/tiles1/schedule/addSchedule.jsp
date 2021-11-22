<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%	String ctxPath = request.getContextPath(); %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정 추가 모달창</title>

<!-- Required meta tags -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> 

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/bootstrap-4.6.0-dist/css/bootstrap.min.css" > 

<!-- Font Awesome 5 Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<!-- 직접 만든 CSS 1 -->
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/style1.css" />

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js" ></script> 
<script type="text/javascript" src="<%= ctxPath%>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script> 

<%--  ===== 스피너를 사용하기 위해  jquery-ui 사용하기 ===== --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/jquery-ui-1.11.4.custom/jquery-ui.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/jquery-ui-1.11.4.custom/jquery-ui.js"></script>

<%-- *** ajax로 파일을 업로드할때 가장 널리 사용하는 방법 ==> ajaxForm *** --%>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery.form.min.js"></script>

<style type="text/css">
	
	#title {
		font-weight: bold;
		vertical-align: top; 
		padding-top: 7px;
	} 
	table {
		border-collapse: separate;
  		border-spacing: 0 10px;
	}

</style>

<script type="text/javascript">
	
	
	
	
	
</script>

</head>

<body>
	<form name="addFrm">
	
		<table id= "tblAddSchedule" class="w-90 mx-auto">
			<tbody>
				<tr>
					<td style="width: 20%;" id="title">캘린더&nbsp;</td>
					<td style="width: 80%; text-align: left;">
					<select id="calname" name="calname" style="width: 100%" class="form-control">
						<option>캘린더1</option>
						<option>캘린더2</option>
					</select>
				</tr>
				<tr>
					<td style="width: 20%;" id="title">일정 제목&nbsp;</td>
					<td style="width: 80%;">
						<input type="text" id="title" name="title" class="form-control" />
					</td>
				</tr>
				<tr>
					<td style="width: 20%;" id="title">시작&nbsp;</td>
					<td style="width: 99%; margin-left: 1px;" class="row">
						<input type="date" id="startDay" name="startDay" class="form-control col-7" />
						<input type="time" id="startTime" name="startTime" class="form-control col-5" />
					</td>
				</tr>
				<tr>
					<td style="width: 20%;" id="title">종료&nbsp;</td>
					<td style="width: 99%; margin-left: 1px;" class="row">
						<input type="date" id="endDay" name="endDay" class="form-control col-7" />
						<input type="time" id="endTime" name="endTime" class="form-control col-5" />
					</td>
				</tr>
				<tr>
					<td style="width: 20%;" id="title">장소&nbsp;</td>
					<td style="width: 80%;">
						<input type="text" id="location" name="location" class="form-control" />
					</td>
				</tr>
				<tr>
					<td style="width: 20%;" id="title">내용&nbsp;</td>
					<td style="width: 80%;"><textarea id="content" name="content" rows="4" cols="80"  class="form-control"> </textarea></td>
				</tr>
				<tr>
					<td rowspan="2" style="width: 20%;" id="title" >알림&nbsp;</td>
					<td style="width: 80%; margin-left: 1px;" class="row">
						<label class="mt-2 col-5"><input type="checkbox" id="notice" name="notice" /> 문자</label>
						<select id="mnoticeTime" name="mnoticeTime" class="form-control col-7">
							<option value="1">30분 전</option>
							<option value="2">1시간 전</option>
							<option value="3">하루 전</option>
						</select>
					</td>
				</tr>
				<tr>					
					<td style="width: 80%; margin-left: 1px;" class="row">
						<label class="mt-2 col-5"><input type="checkbox" id="notice" name="notice" /> 이메일</label>
						<select id="enoticeTime" name="mnoticeTime" class="form-control col-7">
							<option value="1">30분 전</option>
							<option value="2">1시간 전</option>
							<option value="3">하루 전</option>
						</select>
					</td>
				</tr>

			</tbody>
		</table>
	</form>
</body>
</html>