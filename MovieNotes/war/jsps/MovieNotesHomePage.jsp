
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.HashMap,java.util.Iterator"%>
<%@page import="com.movienotes.User"%>
<%@ page import="java.util.ArrayList,com.movienotes.Movie;"%>

<%
	response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>
<head>
<link href="../../css/evoslider.css" type="text/css" rel="Stylesheet" />
<link href="../../css/default/default.css" type="text/css"
	rel="Stylesheet" />


<link type="text/css" rel="stylesheet"
	href="../../css/bootstrap-switch.min.css" />
<script language="JavaScript" type="text/javascript"
	src="../../jquery_min.js"></script>

<script src="../../jquery_migrate.js"></script>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="../../css/jquery-ui-1.10.4.custom.min.css">
<link type="text/css" rel="stylesheet" href="../../MNStyles.css" />
<link type="text/css" rel="stylesheet"
	href="../../css/chosen.min.css" />

<link type="text/css" rel="stylesheet"
	href="../../css/jquery.rating.css" />
<script src="../../js/jquery.easing.1.3.js" type="text/javascript"></script>
<script src="../../js/jquery.evoslider.lite-1.1.0.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="../../MNScripts.js"></script>
<script language="JavaScript" type="text/javascript"
	src="../../jquery_ui.js"></script>
<script language="JavaScript" type="text/javascript"
	src="../../js/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="../../js/chosen.jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery.rating.pack.js"></script>
<script type="text/javascript" src="../../js/json.js"></script>


</head>

<body>
	<div id="fb-root"></div>
	<div id="MovieNotesPage">
		<%
			HttpSession serv = request.getSession();
			// 	ArrayList<Movie> activeDevices =  (ArrayList<Movie>)request.getAttribute("ActiveList");
			// ArrayList<Movie> inactiveDevices =  (ArrayList<Movie>)request.getAttribute("InActiveList");
			 
			String username = (String) serv.getAttribute("loggedinUserName");
			Long loggedinuser = (Long) serv.getAttribute("loggedinUserId");
			Boolean me = (Boolean) request.getAttribute("me");
			User userObj = (User)request.getAttribute("user");
			String trust = (String)request.getAttribute("trust");
			int rating = request.getAttribute("rating")!=null?(Integer)request.getAttribute("rating"):0;
			username = (String)userObj.getName();
			Long fbid = (Long)userObj.getFacebookUserID();
			
			if(fbid.equals(loggedinuser))
				me=true;
		%>
		<div class="container">
			<div class="headTop header">
				<div class="logo" id="topcompanylogo">
					<div>
						<div>
							<img id="profileImg" src="" style="height: 80px; width: 80px;">
						</div>
						<div
							style="font-size: 16px; font-family: cursive; padding-top: 4px;"><%=username%>
							<span style="font-size: 11px; font-family: cursive;"> ,
								10+ Movies</span>
						</div>

					</div>
				</div>
				<div class="tr" id="header-right">
					<a href="javascript:logOut()">Logout</a>
				</div>


				<div id="preferences" style="position: relative; float: left;">
					<div id="prefers"
						style="font-weight: bold; font-size: 13px; margin-bottom: 6px; margin-left: 3px;">
						Preferences</div>

					<div style="padding-bottom: 5px;">
						<input name="star1" type="radio" class="star1" value="1"
							<%=userObj.getInterest_A()==1?"checked='checked'":""%> /> <input
							name="star1" type="radio" class="star1" value="2"
							<%=userObj.getInterest_A()==2?"checked='checked'":""%> /> <input
							name="star1" type="radio" class="star1" value="3"
							<%=userObj.getInterest_A()==3?"checked='checked'":""%> /> <input
							name="star1" type="radio" class="star1" value="4"
							<%=userObj.getInterest_A()==4?"checked='checked'":""%> /> <input
							name="star1" type="radio" class="star1" value="5"
							<%=userObj.getInterest_A()==5?"checked='checked'":""%> /><label
							style="font-family: verdana; padding-left: 10px;"> Action</label>
					</div>
					<div style="padding-bottom: 5px;">
						<input name="star2" type="radio" class="star2" value="1"
							<%=userObj.getInterest_C()==1?"checked='checked'":""%> /> <input
							name="star2" type="radio" class="star2" value="2"
							<%=userObj.getInterest_C()==2?"checked='checked'":""%> /> <input
							name="star2" type="radio" class="star2" value="3"
							<%=userObj.getInterest_C()==3?"checked='checked'":""%> /> <input
							name="star2" type="radio" class="star2" value="4"
							<%=userObj.getInterest_C()==4?"checked='checked'":""%> /> <input
							name="star2" type="radio" class="star2" value="5"
							<%=userObj.getInterest_C()==5?"checked='checked'":""%> /><label
							style="font-family: verdana; padding-left: 10px;">comedy</label>
					</div>
					<div style="padding-bottom: 5px;">
						<input name="star3" type="radio" class="star3" value="1"
							<%=userObj.getInterest_R()==1?"checked='checked'":""%> /> <input
							name="star3" type="radio" class="star3" value="2"
							<%=userObj.getInterest_R()==2?"checked='checked'":""%> /> <input
							name="star3" type="radio" class="star3" value="3"
							<%=userObj.getInterest_R()==3?"checked='checked'":""%> /> <input
							name="star3" type="radio" class="star3" value="4"
							<%=userObj.getInterest_R()==5?"checked='checked'":""%> /> <input
							name="star3" type="radio" class="star3" value="5"
							<%=userObj.getInterest_R()==5?"checked='checked'":""%> /><label
							style="font-family: verdana; padding-left: 10px;">
							Romance</label>
					</div>
					<div style="padding-bottom: 5px;">
						<input name="star4" type="radio" class="star4" value="1"
							<%=userObj.getInterest_S()==1?"checked='checked'":""%> /> <input
							name="star4" type="radio" class="star4" value="2"
							<%=userObj.getInterest_S()==2?"checked='checked'":""%> /> <input
							name="star4" type="radio" class="star4" value="3"
							<%=userObj.getInterest_S()==3?"checked='checked'":""%> /> <input
							name="star4" type="radio" class="star4" value="4"
							<%=userObj.getInterest_S()==4?"checked='checked'":""%> /> <input
							name="star4" type="radio" class="star4" value="5"
							<%=userObj.getInterest_S()==5?"checked='checked'":""%> /><label
							style="font-family: verdana; padding-left: 10px;">
							Storyline</label>
					</div>
				</div>

				<div id="ratings" style="position: relative; float: left;padding-left: 60px;">
					<div id="ratings"
						style="font-weight: bold; font-size: 13px; margin-bottom: 6px; margin-left: 3px;">
						Rating</div>
					<div
						style="margin-bottom:10px;border: 2px dashed #808080; border-radius: 20px; float: left; font-size: 26px; margin-left: 10px; padding: 7px;">
						<span> <%=trust%> / 10 </span>
					</div>
					<%if(!me){ %>
					<div style="padding-top: 5px;margin-left: 10px;" id="trustdiv">
					<img src="/images/thumbsup.png" class='<%=rating==10?"slt":""%>' style="height: 15px;padding: 2px;cursor: pointer;" onclick="javascript:changeTrust(<%=fbid%>,2,this)"/>
					<img src="/images/thumbsdown.png" class='<%=rating==0?"slt":""%>' style="height: 15px;padding: 2px;cursor: pointer;" onclick="javascript:changeTrust(<%=fbid%>,0,this)"/>
					<span style="height: 15px;padding: 2px;"><a id="maybert" class='<%=rating==5?"slt":""%>' style="text-decoration: none;cursor: pointer;" href="javascript:changeTrust(<%=fbid%>,1,this);">Maybe</a></span>					
					</div>
					<%} %>
				</div>
				<%
					if(!me){
				%>
				<div
					style="position: relative; float: left; padding-left: 100px; padding-top: 10px;">
					<div id="format" style="padding-bottom: 5px;">
						<input class="format" type="checkbox" id="check1"><label
							for="check1">Suggest a Movie</label>
					</div>
					<div id="format">
						<input class="format" type="checkbox" id="check2"><label
							for="check2">Unfriend</label>
					</div>
				</div>
				<%
					}else{
				%>
				<div
					style="position: relative; float: left; padding-left: 100px; padding-top: 10px;">
					<div id="format" style="padding-bottom: 5px;">
						<input class="format" onclick="openAddMovie()" type="checkbox" id="check1"><label
							for="check1">Add Movie</label>
					</div>
					<div id="format">
						<input class="format" type="checkbox" id="check2"><label
							for="check2">View Recommendations</label>
					</div>
				</div>
				<%
					}
				%>

				<div
					style="position: relative; float: right; padding-top: 46px; margin-right: -6px;">
					<div id="radio1" style="padding: 5px; float: left;">
						<a href=""
							style="color: #2E6E9E; font-weight: bold; text-decoration: none; font-size: 14px;">Friends
						</a>
					</div>

					<div
						style='<%=!me?"visibility:hidden;":""%>float: left; padding-right: 10px;'>
						<img src="../../images/addFriend_1.png"
							style="height: 15px; cursor: pointer;"
							onclick="javascript:showhideflist();" />
					</div>
					<div style="float: left; visibility: hidden; opacity: 0;" id="fbar">
						<input id="friend_selector" type="text" name="friend"
							style="padding-left: 3px; height: 25px; border: 1px solid #F3F0EB;" />
					</div>
				</div>



			</div>


			<div class="navbar"></div>

			<div class="devicesPanel">
				<div id="tabs" class="home">
					<ul>
						<li><a href="#tabs-1" onclick="javascript:getListing(1,<%=me%>,<%=fbid%>)">To-Watch</a></li>
						<li><a href="#tabs-2" onclick="javascript:getListing(2,<%=me%>,<%=fbid%>)">May-Be</a></li>
						<li><a href="#tabs-4" onclick="javascript:getListing(4,<%=me%>,<%=fbid%>)">Favorites</a></li>
						<li><a href="#tabs-3" onclick="javascript:getListing(3,<%=me%>,<%=fbid%>)">Watched</a></li>						
						<li><a href="#tabs-5" onclick="javascript:getListing(5,<%=me%>,<%=fbid%>)">Recommendations</a></li>
					</ul>
					
					<%
					
					HashMap moviesList = (HashMap)request.getAttribute("moviesList");
					ArrayList favsMap = (ArrayList)request.getAttribute("favs");
					
					request.setAttribute("moviesList",moviesList);
					request.setAttribute("favs",favsMap);
					request.setAttribute("me",me);
					request.setAttribute("fbid",fbid);
					%>
					<jsp:include page="movieslist.jsp"/>
					

				</div>
				<div id="tabs2"
					style="float: right; margin-right: 55px; margin-top: -45px;">
					<%
					HashMap<Long,Long> friends = (HashMap<Long,Long>)request.getAttribute("friends");	
										int i=0;
										Iterator fsitr = friends.keySet().iterator();
											while(fsitr.hasNext()){
												Long id=(Long)fsitr.next();
										if(i%4==0 && i!=0){
					%>
				</div>
				<%
					}

									if(i%4==0){
				%>
				<div class="pics">
					<%
						}
					%>
					<span class="picsspan" id="<%=id%>"><img
						src="../../images/csk11.png"
						onclick="window.location.href='/MovieNotes/profiles/<%=id%>'"
						style="cursor: pointer; height: 50px"></span>

					<%
						i++;
					}
											
											if(i>0 && i%4!=0){
					%>
				</div>
				<%
					}else if(i==0){
				%>
				<div class="pics"></div>
				<% } %>

			</div>
		</div>

		<div class="messageBox" style="display: none;"></div>
		<div class="addMovie" style="display: none;">
		<div style="float: right"><a style="text-decoration: none;color: #2E6E9E;padding: 10px;" href="javascript:closeAddMovie()">close</a></div>
		<div style="padding: 10px;">
		<label class="bld">Imdb search</label>
		</div>
		<div style="padding:10px;">
		<div style="padding-bottom: 30px;padding-left: 20px;"><span><label style="padding-right: 40px;">Movie Name:</label><input type="text" id="mname_i" autocomplete="off" placeholder="movie name" style="padding-right: 20px;"></span> <span style="float: right;margin-right: 10px;font-size: 12px;">
						<input onclick="searchImdb()" class="format" type="checkbox" id="check12"><label
							for="check12" style="font-size: 14px;border:1px solid #2E6E81">Search</label></span>
					</div>
		<div id="results" style="display:none">
		
		</div>
		<div style="position:relative;left:50%;width:30px;padding-bottom: 20px;font-size: 18px;font-family: cursive;vertical-align: middle;">Or</div>
		
		<div style="float: left;padding-right: 0px;padding-left: 20px;">
			<div style="padding-bottom: 7px;"><label> Movie Name:</label></div>
		<div style="padding-bottom: 7px;"><label>Cast:</label></div>
		<div style="padding-bottom: 7px;"><label>Genre:</label></div>	
		<div style="padding-bottom: 7px;"><label>Image Url:</label></div>			
		</div>
		<div style="float: left;padding-bottom: 10px;padding-left: 40px;">
		<div style="padding-bottom: 5px;"><input type="text" id="mm_name" autocomplete="off" placeholder="Name"/></div>
		<div style="padding-bottom: 5px;"><input type="text" id="mm_cast" autocomplete="off" placeholder="Cast"/> </div>
		<div style="padding-bottom: 5px;"><span><input type="text" id="mm_genre" autocomplete="off" placeholder="Genre"/></span></div>
		<div style="padding-bottom: 5px;"><span><input type="text" id="mm_url" autocomplete="off" placeholder="Image Url"/></span>
					
		</div>
		</div> 
		<div style="float: left;padding-left: 10px;padding-bottom: 10px;">
		<span style="display: block;min-height: 50px;"><img id="mm_img" src="" style="display: none;height: 50px;"/></span>
			<span style="float: right;margin-right: 10px;font-size: 12px;padding-top: 5px;"><input onclick="saveMovie_m(true)" class="format" type="checkbox" id="check123"><label
							for="check123" style="font-size: 14px;border:1px solid #2E6E81">Add</label></span>		
		</div>
		
		<div style="width: 600px;">
			<span style="float: right;font-size: 12px;margin-right: 14px;"><select data-placeholder="add to" id="listTo" style="width: 150px;"><option value="0">None</option><option value="1" selected="selected">To Watch</option><option value="2">May be</option> <option value="3">Watched</option></select></span>		
		</div>
		</div>
		</div>

	</div>
	<div class="movieInfo" style="display: none;">
	
	</div>
	
	<div id="loading" style="position: absolute;top:3%;left: 50%;border: 1px solid #999999; background-color: #55AA77;padding: 2px;display: none;"> Loading .. .. </div>
</body>
</html>

<script language="JavaScript" type="text/javascript">
	$(document).ready(
			function() {
				
				$(document).ajaxStart(function() {
					  $("#loading").show();
					});

					$(document).ajaxStop(function() {
					  $("#loading").hide();
					});
					

				window.fbAsyncInit = function() {
					FB.init({
						appId : '697913690251246',
						status : true, // check login status
						cookie : true, // enable cookies to allow the server to access the session
						xfbml : true
					// parse XFBML
					});

					// Here we subscribe to the auth.authResponseChange JavaScript event. This event is fired
					// for any authentication related change, such as login, logout or session refresh. This means that
					// whenever someone who was previously logged out tries to log in again, the correct case below 
					// will be handled. 
					FB.Event.subscribe('auth.authResponseChange', function(
							response) {
						// Here we specify what we do with the response anytime this event occurs. 
						if (response.status === 'connected') {
							// The response object is returned with a status field that lets the app know the current
							// login status of the person. In this case, we're handling the situation where they 
							// have logged in to the app.
							//      nextPage();
							init(
<%=me%>
	);
						} else if (response.status === 'not_authorized') {
							// In this case, the person is logged into Facebook, but not into the app, so we call
							// FB.login() to prompt them to do so. 
							// In real-life usage, you wouldn't want to immediately prompt someone to login 
							// like this, for two reasons:
							// (1) JavaScript created popup windows are blocked by most browsers unless they 
							// result from direct interaction from people using the app (such as a mouse click)
							// (2) it is a bad experience to be continually prompted to login upon page load.
							FB.login();
						} else {
							// In this case, the person is not logged into Facebook, so we call the login() 
							// function to prompt them to do so. Note that at this stage there is no indication
							// of whether they are logged into the app. If they aren't then they'll see the Login
							// dialog right after they log in to Facebook. 
							// The same caveats as above apply to the FB.login() call here.
							FB.login();
						}
					});
				};

				// Load the SDK asynchronously
				(function(d) {
					var js, id = 'facebook-jssdk', ref = d
							.getElementsByTagName('script')[0];
					if (d.getElementById(id)) {
						return;
					}
					js = d.createElement('script');
					js.id = id;
					js.async = true;
					js.src = "//connect.facebook.net/en_US/all.js";
					ref.parentNode.insertBefore(js, ref);
				}(document));

				$("#sortable1, #sortable2").sortable().disableSelection();
				var $tabs = $("#tabs").tabs();

				$(".format").button();
				$('input.star').rating({
					callback : function(value, link) {
						console.log(value);
					}
				});

				var me =
<%=me%>
	;
				console.log(me);
				if (!me) {
					$('input.star1').rating();
					$('input.star2').rating();
					$('input.star3').rating();
					$('input.star4').rating();
					$('input.star1').rating('readOnly', true);
					$('input.star2').rating('readOnly', true);
					$('input.star3').rating('readOnly', true);
					$('input.star4').rating('readOnly', true);
				} else {
					$('input.star1').rating({
						callback : function(value, link) {
							updatePreferences(value, 1)
						}
					});
					$('input.star2').rating({
						callback : function(value, link) {
							updatePreferences(value, 2)
						}
					});
					$('input.star3').rating({
						callback : function(value, link) {
							updatePreferences(value, 3)
						}
					});
					$('input.star4').rating({
						callback : function(value, link) {
							updatePreferences(value, 4)
						}
					});
					
					$("#listTo").chosen({disable_search_threshold: 5});
					 $("#listTo_chosen").css({"width":"100px"}); 
				}

				function init(me) {
					if (me) {
						FB.api('/me/picture?type=normal',
								function(responsepic) {
									$("#profileImg").attr("src",
											responsepic.data.url);
								});
						setupFriendCompleter('#friend_selector');
					} else {
						FB.api('/me/picture?type=normal',
								function(responsepic) {									
									setupFriendCompleter('#friend_selector',<%=fbid%>,<%=loggedinuser%>,responsepic.data.url);
								});
						
					}

				}

			});
</script>