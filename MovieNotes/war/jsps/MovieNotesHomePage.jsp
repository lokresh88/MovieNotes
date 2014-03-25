

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.ArrayList,com.movienotes.Movie;"%>
<%
	HttpSession serv = request.getSession();
	// 	ArrayList<Movie> activeDevices =  (ArrayList<Movie>)request.getAttribute("ActiveList");
	// ArrayList<Movie> inactiveDevices =  (ArrayList<Movie>)request.getAttribute("InActiveList");
	String username = (String) serv.getAttribute("loggedinUserName");
%>
<div class="container">
	<div class="headTop header">
		<div class="logo" id="topcompanylogo">
			<div>
				<div>
					<img id="profileImg" src=""> <span id="prefers"
						style="position: relative; display: inline-block; font-family: cursive; font-size: 13px; margin-right: 50px; margin-top: 35px; float: right;">
						Prefers </span>
				</div>
				<div style="font-size: 16px; font-family: cursive;"><%=username%>
					<span style="font-size: 11px; font-family: cursive;"> has
						watched 10 Movies</span>
				</div>

			</div>
		</div>
		<div class="tr" id="header-right">
			<a href="javascript:logOut()">Logout</a>
		</div>


		<div id="preferences"
			style="position: relative; display: inline-block;">
			<div class="slider" id="sld1"
				style="width: 260px; height: 12px; margin-bottom: 10px"></div>
			<div class="slider" id="sld2"
				style="width: 260px; height: 12px; margin-bottom: 10px"></div>
			<div class="slider" id="sld3"
				style="width: 260px; height: 12px; margin-bottom: 10px"></div>
			<div class="slider" id="sld4"
				style="width: 260px; height: 12px; margin-bottom: 10px"></div>
		</div>

		<div id="prefersInter"
			style="position: relative; display: inline-block;">
			<div class="slider1"
				style="width: 60px; padding-bottom: 8px; margin-top: -2px;">Action</div>
			<div class="slider1" style="width: 60px; padding-bottom: 8px">Comedy</div>
			<div class="slider1" style="width: 60px; padding-bottom: 8px">Romance</div>
			<div class="slider1" style="width: 60px; padding-bottom: 8px">Story</div>
		</div>

		<div style="position: relative; display: inline-block;">
			<div id="radio">
				<a href="" style="text-decoration: underline; color: #357FD4;">Suggest</a>
				| <a href="" style="text-decoration: underline; color: #357FD4;">Ask
					Opinion</a>
			</div>
		</div>

		<div style="position: relative; display: inline-block;padding-left: 86px;">
			<div id="radio1" style="padding: 5px;display: inline-block;">
				<a href="" style="color: #357FD4;text-decoration: none;font-size: 16px;">Friends (12)</a>
			</div>
			<div  style="float: right;">
				<img src="../images/AddFriend.png" style="height: 23px;"></a>
			</div>	

		</div>


	</div>


	<div class="navbar"></div>

	<div class="devicesPanel">
		<div id="tabs" class="home" >
			<ul>
				<li><a href="#tabs-1">To Watch List</a></li>
				<li><a href="#tabs-2">All-Time Favorites</a></li>
				<li><a href="#tabs-2">May Be List</a></li>
				<li><a href="#tabs-2">Recommendations</a></li>
			</ul>
			<div id="tabs-1">
				<ul id="sortable1" class="connectedSortable ui-helper-reset">
					<li class="ui-state-default"><div
							style="display: inline-block;">
							<img
								src="http://ia.media-imdb.com/images/M/MV5BMTk4ODQzNDY3Ml5BMl5BanBnXkFtZTcwODA0NTM4Nw@@._V1_SX214_.jpg"
								style="height: 50px;" />
						</div>
						<div style="display: inline-block; padding-left: 13px;">
							<p>The Dark Knight Rises</p>
							<p>Action</p>
						</div>
						<div
							style="display: inline-block; position: absolute; right: 20%;">
							<p>Imdb Rating : 8</p>
							<p>Movie Notes : 9</p>
						</div></li>
					<li class="ui-state-default"><div
							style="display: inline-block;">
							<img
								src="http://ia.media-imdb.com/images/M/MV5BMjI5OTYzNjI0Ml5BMl5BanBnXkFtZTcwMzM1NDA1OQ@@._V1_SY317_CR1,0,214,317_.jpg"
								style="height: 50px;" />
						</div>
						<div style="display: inline-block; padding-left: 13px;">
							<p>Man of Steel</p>
							<p>Action</p>
						</div>
						<div
							style="display: inline-block; position: absolute; right: 20%;">
							<p>Imdb Rating : 7</p>
							<p>Movie Notes : 6</p>
						</div></li>
					<li class="ui-state-default"><div
							style="display: inline-block;">
							<img
								src="http://ia.media-imdb.com/images/M/MV5BMjMyOTM4MDMxNV5BMl5BanBnXkFtZTcwNjIyNzExOA@@._V1_SX214_.jpg"
								style="height: 50px;" />
						</div>
						<div style="display: inline-block; padding-left: 13px;">
							<p>The Amazing Spider-Man</p>
							<p>Action</p>
						</div>
						<div
							style="display: inline-block; position: absolute; right: 20%;">
							<p>Imdb Rating : 6</p>
							<p>Movie Notes : 6</p>
						</div></li>
					<li class="ui-state-default"><div
							style="display: inline-block;">
							<img
								src="http://ia.media-imdb.com/images/M/MV5BNjE5MzYwMzYxMF5BMl5BanBnXkFtZTcwOTk4MTk0OQ@@._V1_SY317_CR0,0,214,317_.jpg"
								style="height: 50px;" />
						</div>
						<div style="display: inline-block; padding-left: 13px;">
							<p>Gravity</p>
							<p>Story</p>
						</div>
						<div
							style="display: inline-block; position: absolute; right: 20%;">
							<p>Imdb Rating : 8</p>
							<p>Movie Notes : 9</p>
						</div></li>
					<li class="ui-state-default"><div
							style="display: inline-block;">
							<img
								src="http://ia.media-imdb.com/images/M/MV5BMTk2NTI1MTU4N15BMl5BanBnXkFtZTcwODg0OTY0Nw@@._V1_SY317_CR0,0,214,317_.jpg"
								style="height: 50px;" />
						</div>
						<div style="display: inline-block; padding-left: 13px;">
							<p>The Avengers</p>
							<p>Action</p>
						</div>
						<div
							style="display: inline-block; position: absolute; right: 20%;">
							<p>Imdb Rating : 8</p>
							<p>Movie Notes : 9</p>
						</div></li>
				</ul>
			</div>
			<div id="tabs-2">
				<ul id="sortable2" class="connectedSortable ui-helper-reset">
					<li class="ui-state-highlight">Movie 1</li>
					<li class="ui-state-highlight">Movie 2</li>
					<li class="ui-state-highlight">Movie 3</li>
					<li class="ui-state-highlight">Movie 4</li>
					<li class="ui-state-highlight">Movie 5</li>
				</ul>
			</div>

		</div>
	<div id="tabs2" style="float: right;margin-right: 55px;
    margin-top: -35px;">
	<div style="background-color:#F3F0EB;">	
	<span><img src="../images/csk11.png" style="height: 50px"></span>
	<span><img src="../images/csk11.png" style="height: 50px"></span>
	<span><img src="../images/csk11.png" style="height: 50px"></span>	
	<span><img src="../images/csk11.png" style="height: 50px"></span>	
	</div>
	<div style="background-color:#F3F0EB;">	
	<span><img src="../images/csk11.png" style="height: 50px"></span>
	<span><img src="../images/csk11.png" style="height: 50px"></span>
	<span><img src="../images/csk11.png" style="height: 50px"></span>	
	<span><img src="../images/csk11.png" style="height: 50px"></span>	
	</div>
	<div style="background-color:#F3F0EB;">	
	<span><img src="../images/csk11.png" style="height: 50px"></span>
	<span><img src="../images/csk11.png" style="height: 50px"></span>
	<span><img src="../images/csk11.png" style="height: 50px"></span>
<span><img src="../images/csk11.png" style="height: 50px"></span>	
	</div>
	<div style="background-color:#F3F0EB;">	
	<span><img src="../images/csk11.png" style="height: 50px"></span>
	<span><img src="../images/csk11.png" style="height: 50px"></span>
	<span><img src="../images/csk11.png" style="height: 50px"></span>	
	<span><img src="../images/csk11.png" style="height: 50px"></span>	
	</div>
	</div>
</div>

<div class="messageBox" style="display: none;"></div>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function() {
		FB.api('/me/picture?type=normal', function(responsepic) {
			console.log("1");
			$("#profileImg").attr("src", responsepic.data.url);
			console.log(responsepic);
		});

		$(".slider").slider({
			value : 60,
			orientation : "horizontal",
			range : "min",
			animate : true
		});
	});

	$("#sortable1, #sortable2").sortable().disableSelection();
	var $tabs = $("#tabs").tabs();

	$("#radio").buttonset();
</script>
