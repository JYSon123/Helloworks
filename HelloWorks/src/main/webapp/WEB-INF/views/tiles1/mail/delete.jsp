<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<script type="text/javascript">
	
	$(document).ready(function(){
		
		// 완료 버튼
		$("button#btnDelete").click(function(){
			
			// 폼(form)을 전송(submit)
			var frm = document.delFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/deleteEnd.action";
			
			frm.submit();
		});
		
	});//end of $(document).ready(function(){ -------------------------------
	
</script>    

<div style="display:flex; ">
	<div style="margin: auto; padding-left: 3%; ">
	
		<h2 style="margin-bottom: 30px;">글삭제</h2>
		
		<form name="delFrm">
			<table style="width: 1024px" class="table table-bordered">
				<tr>
					<th style="width: 15%; background-color: #DDD">글암호</th>
					<td>
						<input type="hidden" name="mailseq" value="${requestScope.mailseq}" />
						<input type="password" name="pw" id="pw" />
					</td>
				</tr>
			</table>
			
			<div style="margin: 20px;">
				<button type="button" class="btn btn-secondary btn-lg mr-3" id="btnDelete">글삭제 완료</button>
				<button type="button" class="btn btn-secondary btn-lg" onclick="javascript:history.back()">글삭제 취소</button>
			</div>
		</form>
	</div>
</div>
