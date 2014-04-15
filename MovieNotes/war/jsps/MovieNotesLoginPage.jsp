<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>
<head>
<link href="../css/evoslider.css" type="text/css" rel="Stylesheet"/>
<link href="../css/default/default.css" type="text/css" rel="Stylesheet"/>


<link type="text/css" rel="stylesheet" href="../css/bootstrap-switch.min.css" />
<script language="JavaScript" type="text/javascript"
	src="../jquery_min.js"></script>	

	<script src="../jquery_migrate.js"></script>     
     
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" href="../css/jquery-ui-1.10.4.custom.min.css">		
    <link type="text/css" rel="stylesheet" href="../MNStyles.css" />    
    <link type="text/css" rel="stylesheet" href="../css/jquery.rating.css" />  
<script src="../js/jquery.easing.1.3.js" type="text/javascript"></script>
<script src="../js/jquery.evoslider.lite-1.1.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../MNScripts.js"></script>	
<script language="JavaScript" type="text/javascript"
	src="../jquery_ui.js"></script>
<script language="JavaScript" type="text/javascript"
	src="../js/bootstrap-switch.min.js"></script>	
	<script type="text/javascript" src="../js/jquery.rating.pack.js"></script>
	

</head>

<body>
	<div id="fb-root"></div>	
	<div id="MovieNotesPage">
	
	<div id="mainContent">
		<div class="heading"><div style="display: inline-block;margin-top: -12px;"><img src="../images/small-movienotes.png" style="height: 60px;"/></div> <div style="display: inline-block;float: right;line-height: 35.5px;margin-left: -2px;">
		Movie Notes</div> </div>
		<div class="arrowLog"><img src="../images/login.png" style="height: 80px;"/></div>
		
		<div class="loginPanel" id="regDiv">
			<p style="margin: 10px;font-family: cursive;"></p>
			
<!--
  Below we include the Login Button social plugin. This button uses the JavaScript SDK to
  present a graphical Login button that triggers the FB.login() function when clicked. -->
			<fb:login-button show-faces="true" width="200" max-rows="1"></fb:login-button>
		
		</div>
		</div>
		<div id="about" style="top:13%;position: absolute;left: 16%;">
		<div class="evoslider default" id="aboutSlider"> <!-- start evo slider -->
                <dl>
                <dt>Maintain - Movie Lists</dt>
                <dd>
                <div class="evoText partialleft">
	                        <h3>Maintain - Movie Lists</h3>
	                        <p>Movie Notes in simple terms is a list manager</p>
	                        
	                        <ul class="custom-list check">
					            <li>Search and Add movies into your Lists.</li>
					            <li>Favorite Lists / To Watch Lists and so on.</li>
					            <li>Eliminate manual list maintenance.</li>
					            <li>Manage them all by simple clicks.</li>					           	
				            </ul>
	                    </div>
	                    <div class="evoImage" style="width: 350px; padding: 30px;padding-left: 70px;"><img src="../images/Film-2-512.png" style="float: none;height: 200px;"></div>
                </dd>
                <dt>Collaborative Lists</dt>
                <dd><div class="evoText partialleft">
	                        <h3>Collaborative Lists</h3>
	                        <p>Movie Notes also allows users to </p>
	                        
	                        <ul class="custom-list check">
					            <li>Share ratings and reviews</li>
					            <li>Suggest special movies to specific friends</li>
					            <li>Get recommendations based on prefernces and friends circle viewing</li>
					            <li>Collated stats about what your friends think about a movie.</li>					           	
				            </ul>
	                    </div>
	                    <div class="evoImage" style="width: 350px; padding: 30px;padding-left: 70px;"><img src="../images/Film-2-512.png" style="float: none;height: 200px;"></div>
                </dd></dd>
                <dt>Integrated with Facebook</dt>
                <dd><div class="evoText partialleft">
	                        <h3>Integrated with Facebook</h3>
	                        <p>Movie Notes is</p>
	                        
	                        <ul class="custom-list check">
					            <li>Integrated with facebook.</li>
					            <li>Add/Manage friends easily.</li>
					            <li>Trust is easy now.</li>		           	
				            </ul>
	                    </div>
	                    <div class="evoImage" style="width: 350px; padding: 30px;padding-left: 70px;margin-top: 50px;"><img src="../images/Fb-icon.png" style="float: none;height: 70px;"></div>
                </dd></dd>
                </dl>

        </div>
		</div>
		</div>
</body>
</html>

<script language="JavaScript" type="text/javascript">
$(document).ready(function(){

	$("#aboutSlider").evoSlider({
		mode: "accordion", // Sets slider mode ("accordion", "slider", or "scroller")
		width: 960, // The width of slider
		height: 280, // The height of slider
		slideSpace: 5, // The space between slides
		mouse: true, // Enables mousewheel scroll navigation
		keyboard: true, // Enables keyboard navigation (left and right arrows)
		speed: 500, // Slide transition speed in ms. (1s = 1000ms)
		easing: "swing", // Defines the easing effect mode
		loop: true, // Rotate slideshow
		autoplay: true, // Sets EvoSlider to play slideshow when initialized
		interval: 5000, // Slideshow interval time in ms
		pauseOnHover: true, // Pause slideshow if mouse over the slide
		pauseOnClick: true, // Stop slideshow if playing
		directionNav: true, // Shows directional navigation when initialized
		directionNavAutoHide: false, // Shows directional navigation on hover and hide it when mouseout
		controlNav: true, // Enables control navigation
		controlNavAutoHide: false // Shows control navigation on mouseover and hide it when mouseout
		}); 

});

  window.fbAsyncInit = function() {
  FB.init({
    appId      : '697913690251246',
    status     : true, // check login status
    cookie     : true, // enable cookies to allow the server to access the session
    xfbml      : true  // parse XFBML
  });

  // Here we subscribe to the auth.authResponseChange JavaScript event. This event is fired
  // for any authentication related change, such as login, logout or session refresh. This means that
  // whenever someone who was previously logged out tries to log in again, the correct case below 
  // will be handled. 
  FB.Event.subscribe('auth.authResponseChange', function(response) {
    // Here we specify what we do with the response anytime this event occurs. 
    if (response.status === 'connected') {
      // The response object is returned with a status field that lets the app know the current
      // login status of the person. In this case, we're handling the situation where they 
      // have logged in to the app.
      nextPage();
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
  (function(d){
   var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
   if (d.getElementById(id)) {return;}
   js = d.createElement('script'); js.id = id; js.async = true;
   js.src = "//connect.facebook.net/en_US/all.js";
   ref.parentNode.insertBefore(js, ref);
  }(document));

  // Here we run a very simple test of the Graph API after login is successful. 
  // This testAPI() function is only called in those cases. 
  function nextPage() {
    FB.api('/me', function(response) {
    	 //FB.api('/me/picture?type=normal', function(responsepic) {
      		homePage(response.id,response.name);
      		//console.log(responsepic.url);
    	// });
    });
  }



</script>