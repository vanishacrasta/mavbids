// Put your Last.fm API key here
var api_key = "6550578b286e3036975c80601159f37a";

function sendRequest () {
    var request1 = new XMLHttpRequest();
    var method = "artist.getinfo"      //get information about artist
	var output1="";
    var artist = encodeURI(document.getElementById("forminput").value);
    request1.open("GET", "proxy.php?method="+method+"&artist="+artist+"&api_key="+api_key+"&format=json", true);
    request1.setRequestHeader("Accept","application/json");
    request1.onreadystatechange = function () {
        if (this.readyState == 4) {
            var json = JSON.parse(request1.responseText);
            var str = JSON.stringify(json,undefined,2);
            console.log(str);
				output1="<h1 align='center'>"+json.artist.name+"</h1>";
				document.getElementById("output1").innerHTML = output1+'<img src='+json.artist.image[4]['#text']+' width="2" height="2"></br>';
				document.getElementById("output1").innerHTML += '<font size="2" face="verdana"><br>'+json.artist.bio.content+'</font><br><br>';
				}
    };
    request1.send(null);
	

	var request2 = new XMLHttpRequest();
	var method = "artist.getTopAlbums"      //get information about artist's top 3 albums
	var output2=" ";
   request2.open("GET", "proxy.php?method="+method+"&artist="+artist+"&api_key="+api_key+"&format=json&limit=3", true);
    request2.setRequestHeader("Accept","application/json");
    request2.onreadystatechange = function() {
		if (request2.readyState == 4) {
				var json = JSON.parse(request2.responseText);
				var str=JSON.stringify(json,undefined,2);  
          		 output2="<h2 align='center'>TOP ALBUMS</h2>";
      			for(var i=0;i<json.topalbums.album.length;i++)
					{
						output2+='<font size="3" face="verdana">'+json.topalbums.album[i].name+'</font><br/><br>';
						output2+= '<img src='+json.topalbums.album[i].image[1]['#text']+' width="50" height="10"><br><br>';
					}
				document.getElementById("output2").innerHTML=output2;
			}
		};
    request2.send(null);
	
	var request3 = new XMLHttpRequest();
	var method = "artist.getSimilar"         //get information about similar artists
	var output3=" ";
    request3.open("GET", "proxy.php?method="+method+"&artist="+artist+"&api_key="+api_key+"&format=json&limit=3", true);
    request3.setRequestHeader("Accept","application/json");
    request3.onreadystatechange = function() {
		if (request3.readyState == 4) {
				var json = JSON.parse(request3.responseText);           
				var str=JSON.stringify(json,undefined,2);
				output3="<h3 align='center'>SIMILAR ARTISTS</h3>";
				for(var i=0;i<json.similarartists.artist.length;i++)
					{   
				        output3+='<br><font size="3" face="verdana">'+json.similarartists.artist[i].name+'</font><br/><br>';
						output3+='<img src='+json.similarartists.artist[i].image[1]['#text']+' width="10" height="10"><br><br>';
											}   
			document.getElementById("output3").innerHTML=output3;
			}
		};
    request3.send(null);
	
		var request4 = new XMLHttpRequest();
	var method = "artist.getinfo"          //provide link to artist page on last.fm
    request4.open("GET", "proxy.php?method="+method+"&artist="+artist+"&api_key="+api_key+"&format=json&limit=3", true);
    request4.setRequestHeader("Accept","application/json");
    request4.onreadystatechange = function() {
		if (request4.readyState == 4) {
				var json = JSON.parse(request4.responseText);           
				var str=JSON.stringify(json,undefined,2);
		       document.getElementById("output4").innerHTML='<a id="link2" href ="'+json.artist.bio.links.link.href+'">Click to know more about '+json.artist.name+'</a><br><br><br>'; 
			}
		};
		request4.send(null);
}

function musicianName(Name) {
	var query = window.location.search.substring(1);
    var vars = query.split('&');
    for (var i = 0; i < vars.length; i++) {
        var substring = vars[i].split('=');
        if (decodeURIComponent(substring[0]) == Name) {
            return decodeURIComponent(substring[1]);
        }
    }
    console.log('Query variable %s not found', variable);
}

window.onload = function() {
	            SearchKey=musicianName('searchKey');
				console.log(SearchKey);
				if (SearchKey != null){
					 document.forms[0].forminput.value = SearchKey;
					 }
				}
