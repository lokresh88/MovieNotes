<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.HashMap,java.util.Iterator"%>
<%@page import="com.movienotes.User"%>
<%@ page import="java.util.ArrayList,com.movienotes.Movie;"%>

<%
	HashMap moviesList = (HashMap) request.getAttribute("moviesList");
	ArrayList favsMap = (ArrayList)request.getAttribute("favs");

	ArrayList<Movie> movies = new ArrayList<Movie>();
	if(!moviesList.containsKey("movies")){
		return;
	}
	movies = (ArrayList<Movie>)moviesList.get("movies");
	int type = (Integer)moviesList.get("type");
	boolean me = (Boolean)request.getAttribute("me");
	Long fbid = (Long)request.getAttribute("fbid");
%>

<div id="tabs-<%=type%>" class="movs">
	<ul id="sortable1" class="connectedSortable ui-helper-reset">
<%
 if(movies.size()==0){
 %>
 
 No Movies yet :(
 
 <% } %>
		<%
			for (int m = 0; m < movies.size(); m++) {
				Movie mov = movies.get(m);
		%>
		<li class="ui-state-default movies">
			<div style="width: 10%;cursor: pointer;" onclick="javascript:showMovie(<%=me%>,<%=fbid%>,<%=mov.getId()%>)">
				<img src='<%=mov.getUrl()!="N/A"?mov.getUrl():""%>' style="height: 90%;width: 90%" />
			</div>
			<div style="width: 30%;cursor:pointer;" onclick="javascript:showMovie(<%=me%>,<%=fbid%>,<%=mov.getId()%>)">
				<p>
					<span style=""><%=mov.getName()%></span>
				</p>
				<p class="grey"><%=mov.getGenre() != null ? mov.getGenre() : "&nbsp;"%></p>
			</div>
			<div style="cursor: pointer;" onclick="javascript:showMovie(<%=me%>,<%=fbid%>,<%=mov.getId()%>)">
				<p>					
					<%=mov.getYear() != 0 ? mov.getYear() : "&nbsp;"%></p>
				<p>
					<span class="grey"><%=mov.getCast() != null ? mov.getCast() : "&nbsp;"%></span>
				</p>
			</div>
			<div style="width: 15%">
				<p>Imdb Rating : <%=mov.getRating()%></p>
<!-- 				<p onchange=""> -->
<!-- 					Movie Notes : -->
<%-- 					<%=mov.getMnrating()%></p> --%>
			</div>
			<div style="width: 10%;margin-left: 5px;">
				<p>
				<%if(me){ %><span><input <%if(!favsMap.contains(mov.getId())){ %>disabled="disabled"<%} %>  class="format1" type="checkbox" id="FavsL_<%=type%>_<%=mov.getId()%>"/><label
							for="FavsL_<%=type%>_<%=mov.getId() %>" class="inact" style="height: 30px;border:1px solid;width:100px;color:#2E6E9E;margin-top: -5px;margin-bottom: 5px;cursor: pointer;" onclick="markFav('<%=mov.getId()%>',this)">Favorite</label></span><%} %>
				<select  onchange="setOptions('<%=mov.getId()%>',this,<%=me %>,true)" data-placeholder="more actions" id="moreactions_<%=mov.getId()%>" movieId="<%=mov.getId()%>" class="moreActions" style="width: 100px;"><option value="-1" selected="selected">None</option><%if(me){ %><option value="0">Remove</option><%}%><% if(type!=1){ %><option value="1">To Watch</option><%}%> %><% if(type!=2){ %><option value="2">Watched</option><%}%><% if(type!=3){ %> <option value="3">Maybe</option><%} %></select>
			</div>

		</li>		
		<%
			}
		%>
	</ul>
</div>


<script>
try{
$( ".format1" ).button( "destroy" );
}catch(err){	
}
$(".moreActions").chosen({disable_search_threshold: 5});
$("#listTo_chosen").css({"width":"100px"});
$('.format1').button();
</script>


