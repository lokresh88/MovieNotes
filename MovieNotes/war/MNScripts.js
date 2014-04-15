/* Login Page */

function checkLoginAndRedirect() {
	$.post("/validateUser", {}, function(data) {
		if (data != null) {
			$("#MovieNotesPage").html(data);
		}
	});
}

function logOut() {
	$.post("/logoutUser", {}, function(data) {
		window.location.reload();
		FB.logout(function(response) {
			// Person is now logged out
		});
	}, "json");
}

function homePage(fbid, fbname) {
	$.post("/validateUser", {
		"fbid" : encodeURIComponent(fbid),
		"fbname" : encodeURIComponent(fbname)
	}, function(data) {
		var js = data;
		if(data.status){
			window.location.href="/MovieNotes/profiles/me";
		}
	},"json");

}

function updatePreferences(val,type){
	$.post("/userOperations", {
		"val" : val,
		"type":type,
		"action" : "udpatePref"
	}, function(data) {
		
	});	
}

function addFriend(fbid,name) {
	$.post("/userOperations", {
		"fid" : encodeURIComponent(fbid),
		"action" : "addFriend",
		"fbname":name
	}, function(data) {
		//window.location.reload();
		var elm =$("<span class='picsspan' id='"+fbid+"'><img src='' onclick=\"window.location.href='/MovieNotes/profiles/"+fbid+"'\" style='cursor: pointer; height: 50px'/></span>");
		$(".pics:last").append(elm);
		$(elm).find("img").attr("src",getPicForFriend(fbid));
	});

}

function showhideflist() {
	if ($("#fbar").css("visibility") != "visible"){ 
		$('#fbar').css({
			visibility : "visible"
		}).animate({
			opacity : 1
		}, 200);
	$("#friend_selector").focus();
	$("#friend_selector").val("");
	}
	else
		$('#fbar').css({
			visibility : "hidden"
		}).animate({
			opacity : 0
		}, 200);
}

function convertFriendListToCompletable(fbid,curid,curimg) {
	if (window.fbFriendList && !(window.fbFriendList[0].label)) {
		// We have a friendlist, but it's missing the 'label' field that
		// autocomplete likes.
		$.each(window.fbFriendList, function(idx, item) {
			window.fbFriendList[idx].label = item.name;
			window.fbFriendListHash[item.id]=item;
		});
		// Sort it so list makes sense in autocomplete results
		window.fbFriendList.sort(function(a, b) {
			var compA = a.name.toUpperCase();
			var compB = b.name.toUpperCase();
			return (compA < compB) ? -1 : (compA > compB) ? 1 : 0;
		});
	//	console.log(window.fbFriendListHash);
		loadFriendsImages(fbid,curid,curimg);
	}
}

function populateFriendList(fbid,curid,curimg) {
	if (!window.fbFriendList) {
		FB.api('/me/friends', {
			fields : 'name,id,picture'
		}, function(response) {
			window.fbFriendList = response.data;
			window.fbFriendListHash = new Object();
//			console.log(response.data);
			convertFriendListToCompletable(fbid,curid,curimg);
		});
	}
}

function setupFriendCompleter(inputDivId,fbId,curid,curimg) {
	// Look for an existing global 'fbFriendList', either from a previous
	// run of the function, or set into the page at generation time.
	if (!(window.fbFriendList)) {
		populateFriendList(fbId,curid,curimg);
	} else {
		// Make sure the pre-existing friend list is autocomplete-friendly
		convertFriendListToCompletable(fbId,curid,curimg);
		
	}
	
	

	// Make sure we can put our hidden ID somewhere
	if ($('input#fbFriendId').length == 0) {
		$('#fb-root')
				.append(
						"<input id='fbFriendId' name='fbFriendId' type='hidden' value=''/>");
	}

	// Add the custom source and select functions to the autocompleter.
	$(inputDivId).autocomplete(
			{
				source : function(request, response) {
					var matcher = new RegExp($.ui.autocomplete
							.escapeRegex(request.term), "i");
					var suggestions = [];
					$.each(fbFriendList, function(idx, item) {
						friendName = item.name || item;
						if (matcher.test(friendName)) {
							suggestions.push(item);
						}
					});
					response(suggestions);
				},
				select : function(event, ui) {
					var selectedObj = ui.item;
					$('input#fbFriendId').val(selectedObj.id);
					$(this).val(selectedObj.label);
					console.log(selectedObj.id);
					addFriend(selectedObj.id,selectedObj.label);
					showhideflist();
					return false;
				}
			});
}

function loadFriendsImages(fbid,curid,curimg){
	$.each($(".picsspan"), function(idx, item) {
		$(item).find("img").attr("src",getPicForFriend($(item).attr("id")));
		console.log(getNameForFriend($(item).attr("id")));
		$(item).find("img").attr("alt",getNameForFriend($(item).attr("id")));
		
		if(fbid){
			if($(item).attr("id") == curid){
				$(item).find("img").attr("src",curimg);
			}
		}
	});
	
	if(fbid){
		if(fbid == curid){
			$("#profileImg").attr("src",curimg);
		}else
		{
			$("#profileImg").attr("src", getPicForFriend(fbid));
			FB.api('/'+fbid+'/picture?type=large',
					function(responsepic) {		
							$("#profileImg").attr("src", responsepic.data.url);						
					});
		}
	}
}



function getPicForFriend(id) {
	if(window.fbFriendListHash[id]==null){
		return "../../images/csk11.png";
	}else{
		return window.fbFriendListHash[id].picture.data.url;
	}
}

function getNameForFriend(id) {
	if(window.fbFriendListHash[id]==null){
		return "";
	}else{
		return window.fbFriendListHash[id].name;
	}
}

function closeAddMovie()
{
$(".addMovie").slideUp();	
}

function openAddMovie()
{
$(".addMovie").slideDown();	
}


function searchImdb(){
	var name=$.trim($("#mname_i").val());
	
	if(name==""){
		$("#mname_i").focus();
		return;
	}
	
	$.get("http://www.omdbapi.com/", {
		"s" : encodeURIComponent(name)
	}, function(data) {
		$("#results").html("");
		$("#results").show();
		if(data==null || data.Search==null || data.Search.length<=0){
			$("#results").html("<span style='padding:20px;'>No matches !</span>");
			return;
		}
		
		var table = $('<table cellpadding="3px;" style="width:500px;border:1px solid #888888;margin-bottom:10px;position:relative;left:7%;overflow:scroll;font-size:12px;padding:5px;overflow:auto;"></table>');
        for (i = 0; i < data.Search.length && i < 5; i++) {
                row = $('<tr></tr>');
                    var row1 = $('<td style="max-width:100px"></td>').text(data.Search[i].Title);
                    var row2 = $('<td></td>').text(data.Search[i].Year);
                    var row3 = $('<td></td>').append('<a href="javascript:populateMovieInfo(\''+data.Search[i].imdbID+'\');" style="color:#2E6E81">select</a>');                    
                    table.append(row);
                    row.append(row1);
                    row.append(row2);
                    row.append(row3);
                    $(table).append(row);                    
            }
        $("#results").append(table);
},"json");
}

function populateMovieInfo(elm){
	$.get("http://www.omdbapi.com/", {
		"i" : elm
	}, function(data) {
		
	if(data==null){
		$("#results").html("<span style='padding:20px;'>No matches !</span>");
		return;
	}
	$("#mm_img").hide();
	$("#mm_name").val(data.Title);
	$("#mm_name").data("temp",data);
	$("#mm_cast").val(data.Actors);
	$("#mm_genre").val(data.Genre);
	if(data.Poster!="N/A"){
	$("#mm_img").attr("src",data.Poster);
	$("#mm_img").show();
	}
	$("#mm_url").val(data.Poster);
	},"json");
}

function saveMovie_m(me){
	var name = $.trim($("#mm_name").val());
	var cast = $.trim($("#mm_cast").val());
	var url= $.trim($("#mm_url").val());	
	var rating = 0;
	var genre = $.trim($("#mm_genre").val());
	var year = 2014;
	var imdbid = "-1";

	var data = $("#mm_name").data("temp");
	if(data!=undefined && data!=""){
		name = data['Title'];
		cast = data['Actors'] + " "+data['Director'];
		rating = data['imdbRating'];
		if(data['Poster']!="N/A"){
			url=data['Poster'];
		}
		genre = data['Genre'];
		year = data['Year'];
		imdbid = data['imdbID'];
	}else{
		if(name==""){
			$("#mm_name").focus();
			return;
		}
		// save 		
	}
	
	var postparams = {"action":"addMovie","name":name,"cast":cast,"url":url,"rating":rating,"genre":genre,"year":year,"imdbid":imdbid,"addTo":$("#listTo").val()};
	
	$.post("/movieOperations", { params : JSON.stringify(postparams) }
	, function(data) {
		
	if(!data.status){
		$("#results").html("<span style='padding:20px;'>"+data.statusMsg+"!</span>");
		return;
	}
	
	closeAddMovie();
	
	//clear the search
	$("#mm_img").attr("src","");	
	$("#mm_img").hide();
	$("#mm_name").val("");
	$("#mm_genre").val("");
	$("#mm_cast").val("");
	$("#mm_url").val("");
	$("#mname_i").val("");
	$("#results").hide();
	$("#mm_name").attr("temp","");
	
	if(me && $("#listTo").val()>0 && $("#listTo").val()<=3){
		getListing($("#listTo").val());
	}
	
	},"json");
	
	/*data.Title;
	Year
	Genre
	Actors
	Director
	Poster
	imdbRating*/
	
}

function changeTrust(fbid,trust,elm){
	$.post("/userOperations", { 
		"fbid":fbid,
		"trust":trust,
		"action":"trust"
	}
	, function(data) {
		$("#trustdiv").find("img").removeClass("slt");
		$("#trustdiv").find("a").removeClass("slt");
		$(elm).addClass("slt");
		
		if(trust==1){
			$("#maybert").addClass("slt");
		}
		
	});	
}

function markFav(movid,elm1){
	var elm=$(elm1).siblings('input');
	var set = $(elm).is(":disabled")?true:false;
	
	$.post("/movieOperations", { 
		"movid":movid,
		"action":"markFav",
		"set":set
	}
	, function(data) {
		if(data.status){
			console.log(set);
			if(!set)
				$(elm).attr('disabled','disabled');
			else
				$(elm).removeAttr('disabled');
			$(elm).button("refresh");
		}		
	},"json");	
}

function setOptions(movid,elm,me,cls){
	
	var set = $(elm).val();
	if(set<0)
		return;
	
	$.post("/movieOperations", { 
		"movid":movid,
		"action":"setOptions",
		"set":set
	}
	, function(data) {
		if(cls && me && data.status){
			$(elm).parents(".movies").slideUp();
		}		
	},"json");	
}


function getListing(elm,me,fbid){
	var params = { 
			"action":"Listing",
			"set":elm
		};
	if(!me){
		var params = { 
				"action":"Listing",
				"set":elm,
				"fbid":fbid,
			};
	}
	
	$.post("/movieOperations", params
	, function(data) {
		$("#tabs-"+elm).html("");
			$("#tabs-"+elm).remove();			
			$("#tabs").append(data);			
			$("#tabs").tabs("refresh");
	});	
}

function showMovie(me,fbid,movid){
	var params = { 
			"action":"movieInfo",
			"movid":movid
		};
	if(!me){
		var params = { 
				"action":"movieInfo",
				"movid":movid,
				"fbid":fbid,
			};
	}
	
	$.post("/movieOperations", params
	, function(data) {
		
		$(".movieInfo").html(data);
		$(".movieInfo").show();
	});	
}


function setUsersMovieRating(movid){
	
	var val = $('input.ratingsM:checked').val();
	var cmnts = $('#userComments').val();
	
	
	var params = { 
			"action":"movieRating",
			"movid":movid,
			"cmts":cmnts,
			"val":val			
		};

	
	$.post("/movieOperations", params
	, function(data) {		
		
	});	
}
