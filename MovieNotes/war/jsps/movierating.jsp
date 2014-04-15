<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.HashMap,java.util.Iterator"%>
<%@page import="com.movienotes.User"%>
<%@ page import="java.util.HashMap,com.movienotes.MovieRatings;"%>



<%
	HashMap movieratings = (HashMap) request
			.getAttribute("movieRatings");
%>

<%
	Iterator rItr = movieratings.keySet().iterator();
	while (rItr.hasNext()) {
		Long fbid = (Long) rItr.next();
		MovieRatings movrating = (MovieRatings) movieratings.get(fbid);
		Double val2 = movrating.getRating();
		String comments = movrating.getComments();
		int val = (val2).intValue();

%>
<div class="moviesDiv" style="display: inline-block;width: 100%;">
	<div style="width: 10%;">
		<img id="IC_<%=fbid%>" src='' style="height: 70%; width: 70%" />
	</div>
	<div style="width: 40%;font-weight: normal;color: #888888;">
		<%=comments%>
	</div>
	<div style="float: left; width: 15%">

		<input name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="1" <%=val==1?"checked='checked'":""%>/> <input
			name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="2" <%=val==2?"checked='checked'":""%>/> <input
			name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="3" <%=val==3?"checked='checked'":""%>/> <input
			name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="4" <%=val==4?"checked='checked'":""%>/> <input
			name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="5" <%=val==5?"checked='checked'":""%>/> <input
			name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="6" <%=val==6?"checked='checked'":""%>/> <input
			name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="7" <%=val==7?"checked='checked'":""%>/> <input
			name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="8" <%=val==8?"checked='checked'":""%> /> <input
			name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="9" <%=val==9?"checked='checked'":""%> /> <input
			name="ratingsM_<%=fbid%>"  type="radio"
			class="ratingsM_<%=fbid%> {split:2}" value="10" <%=val==10?"checked='checked'":""%> />

		<script>
			var str = "ratingsM_" +
		<%=fbid%>
			+ "";
			$("input."+str).rating({
				"split" : 2,
				"readOnly":true
			});			
			
			var src = getPicForFriend('<%=fbid%>');
			var idstr = "IC_"+"<%=fbid%>";
			$("#"+idstr).attr("src",src);
			
			FB.api('/'+<%=fbid%>+'/picture?type=large',
					function(responsepic) {		
						$("#"+idstr).attr("src", responsepic.data.url);						
					});
			
			
			
			
		</script>

		<%
			}
		%>
		
