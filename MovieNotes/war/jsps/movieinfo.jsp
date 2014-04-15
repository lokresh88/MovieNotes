<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.HashMap,java.util.Iterator"%>
<%@page import="com.movienotes.User"%>
<%@ page import="java.util.ArrayList,com.movienotes.Movie;"%>

<%
	Movie mov = (Movie) request.getAttribute("mov");
	boolean me = (Boolean) request.getAttribute("me");
	Long fbid = (Long) request.getAttribute("fbid");
	ArrayList type = (ArrayList) request.getAttribute("type");
	Boolean favsMap = (Boolean) request.getAttribute("favMap");
	String avgRating = (String) request.getAttribute("avgRating");

	//rating stuff
	String comments = (String) request.getAttribute("comments");
	int rating = ((Double) request.getAttribute("rating")).intValue();
	HashMap movieratings = (HashMap) request
			.getAttribute("movieRatings");
%>

<div style="float: right">
	<a style="text-decoration: none; color: #2E6E9E; padding: 5px;"
		href="javascript:;" onclick="$('.movieInfo').hide()">close</a>
</div>
<div class="moviesDiv">
	<div style="width: 10%;">
		<img src='<%=mov.getUrl() != "N/A" ? mov.getUrl() : ""%>'
			style="height: 90%; width: 90%" />
	</div>
	<div style="width: 30%;">
		<p>
			<span style="font-size: 14px;"><%=mov.getName()%></span>
		</p>
		<p class="grey"><%=mov.getGenre() != null ? mov.getGenre() : "&nbsp;"%></p>
	</div>
	<div>
		<p>
			<%=mov.getYear() != 0 ? mov.getYear() : "&nbsp;"%></p>
		<p>
			<span class="grey"><%=mov.getCast() != null ? mov.getCast() : "&nbsp;"%></span>
		</p>
	</div>
	<div style="width: 25%; margin-left: 45px;">
		<p>
			<label style="font-size: 12px;"> Add to my - </label> <select
				onchange="setOptions('<%=mov.getId()%>',this,<%=me%>,false)"
				data-placeholder="more actions" id="moreactions1_<%=mov.getId()%>"
				movieId="<%=mov.getId()%>" class="moreActions1"
				style="width: 100px;"><option value="-1"
					selected="selected">None</option>
				<%
					if (!type.contains(1)) {
				%><option value="1">To Watch</option>
				<%
					}
				%>
				<%
					if (!type.contains(2)) {
				%><option value="2">Watched</option>
				<%
					}
				%>
				<%
					if (!type.contains(3)) {
				%>
				<option value="3">Maybe</option>
				<%
					}
				%></select>

			<%
				
			%><span style="float: right; margin-top: 5px; margin-right: 65%;"><input
				<%if (!favsMap) {%> disabled="disabled" <%}%> class="format2"
				type="checkbox" id="FavsI_<%=mov.getId()%>" /><label
				for="FavsI_<%=mov.getId()%>" class="inact1"
				style="font-size: 12px; height: 30px; width: 100px; border: 1px solid; color: #2E6E9E; margin-top: 5px; margin-right: -22px; cursor: pointer;"
				onclick="markFav('<%=mov.getId()%>',this)">Favorite</label></span>
			<%
				
			%>
		
	</div>
</div>
<div class="navbar" style="display: inline-block;"></div>

<div class="moviesDiv" style="color: #333333;">
	<div style="width: 35%; height: 10px; font-size: 13px;">
		<span class="blk"> Friends Rating : <%=avgRating %> / </span><span class="blk">Imdb
			Rating : <%=mov.getRating()%>
		</span>
	</div>
</div>
<div class="moviesDiv" style="padding: 3px; padding-top: 40px;">
	<div style="width: 70%">
		<div style="float: left; width: 60%">
			<textarea id="userComments" rows="3" cols="45" placeholder="your comments")></textarea>
		</div>
		<div style="float: left; width: 15%">
			<label
				style="font-family: verdana; font-size: 12px; padding-left: 10px; font-weight: normal;">
				Your Rating </label>
		</div>
		<div style="float: left; width: 20%">

			<input name="ratingsM" type="radio" class="ratingsM {split:2}"
				value="1" /> <input name="ratingsM" type="radio"
				class="ratingsM {split:2}" value="2" /> <input
				name="ratingsM" type="radio" class="ratingsM {split:2}" value="3" />
			<input name="ratingsM" type="radio" class="ratingsM {split:2}"
				value="4" /> <input name="ratingsM" type="radio"
				class="ratingsM {split:2}" value="5" /> <input
				name="ratingsM" type="radio" class="ratingsM {split:2}" value="6" />
			<input name="ratingsM" type="radio" class="ratingsM {split:2}"
				value="7" /> <input name="ratingsM" type="radio"
				class="ratingsM {split:2}" value="8" /> <input
				name="ratingsM" type="radio" class="ratingsM {split:2}" value="9" />
			<input name="ratingsM" type="radio" class="ratingsM {split:2}"
				value="10" /> <input class="format3" type="checkbox"
				id="savemov" /><label for="savemov"
				style="font-size: 12px; color: #2E6E9E; margin-top: 20px; margin-right: 0px; cursor: pointer; border: 1px solid;"
				onclick="setUsersMovieRating('<%=mov.getId()%>')">save</label>
		</div>


	</div>
	<div style="width: 25%">
		<p>Chart</p>
	</div>
</div>


<%
	request.setAttribute("movieRatings", movieratings);
%>
<jsp:include page="movierating.jsp" />




<script>
$(".moreActions1").chosen({disable_search_threshold: 5});
var chStr = "#moreactions1_"+<%=mov.getId()%>+"_chosen";
$(chStr).css({"width":"100px"});
$('.format2').button();
$('.format3').button();

<% if(comments!=null && comments!=""){%>
$('#userComments').html("<%=comments%>");
<%}%>
$('input.ratingsM').rating(   {"split":2,
	callback : function(value, link) {
		setUsersMovieRating('<%=mov.getId()%>');
	}});
$('input.ratingsM').rating('select','<%=rating%>');

</script>
