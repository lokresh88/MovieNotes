
/* Login Page */

function checkLoginAndRedirect(){
	$.post("/validateUser", {
	}, function(data) {
		if(data!=null){
			$("#MovieNotesPage").html(data);
		}
	});
}

function logOut(){
	$.post("/logoutUser", {
	}, function(data) {
			window.location.reload();
			FB.logout(function(response) {
		        // Person is now logged out
		    });
	},"json");
}


function homePage(fbid,fbname){
	$.post("/validateUser", {
		"fbid" : encodeURIComponent(fbid),
		"fbname" : encodeURIComponent(fbname)	
	}, function(data) {
		$("#MovieNotesPage").html(data);
	});
	
}

