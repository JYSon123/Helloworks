<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="template_KJH.jsp"/>

<style type="text/css">
	
	
	
</style>

<script type="text/javascript">
	
	
	
</script>

<!-- !!!!!PAGE CONTENT START!!!!!! -->
<div class="w3-main" style="margin:30px 0 0 300px">

  
   <!-- Contact Section -->
  <div class="w3-container w3-padding-large" style="margin-bottom: 800px;">
    <h4 id="contact" style="color: gray; margin-top: 70px;"><b>자유게시판</b></h4>
    
    <hr class="w3-opacity">
    <form action="/action_page.php" target="_blank">
      <div class="w3-section">
        <label>Name</label>
        <input class="w3-input w3-border" type="text" name="Name" required>
      </div>
      <div class="w3-section">
        <label>Email</label>
        <input class="w3-input w3-border" type="text" name="Email" required>
      </div>
      <div class="w3-section">
        <label>Message</label>
        <input class="w3-input w3-border" type="text" name="Message" required>
      </div>
      <button type="submit" class="w3-button w3-black w3-margin-bottom"><i class="fa fa-paper-plane w3-margin-right"></i>Send Message</button>
    </form>
  </div>
  

<!-- !!!!!PAGE CONTENT END!!!!!! -->
</div>