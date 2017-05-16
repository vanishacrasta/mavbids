<!doctype html>
<html>
<head>
<title>JavaJama Coffee House</title>
<link rel="stylesheet"  href="javajam.css"/>
<style>
#php {
	margin-left:400px;
	border-collapse:collapse;
	}
#php th,td {
	padding:10px;
	text-align: left;
}

#php #link {
	cursor: pointer;
	font:inherit;
	color:blue;
}
#php th {
	background-color:#221811;
	color:#fcebb6;
}
#php tr:nth-child(even){background-color: #dfbf9f}
#php tr:hover{
	background-color:#f9f2ec;
}
</style>
</head>
<body id="wrapper">
<nav class="music" style="height:1138px;">
<img src="javajamlogo.jpg"/>
<table>
<tr><td><a href="home.html">Home</td></tr>
<tr><td><a href="menu.html">Menu</td></tr>
<tr><td><a href="http://localhost/musician.php">Music</td></tr>
<tr><td><a href="jobs.html">Jobs</td></tr>
</table>
</nav>
<header>
<h1>&nbsp;JavaJam Coffee House</h1>
</header>
<article >
<img src="music.JPG" />
<div class="content1">
<h2>Music at JavaJam</h2>
<p>The first friday night each month at JavaJam is a special night. Join us from 8 pm to 11 pm for some music you won't want to miss! </p>
</div>
<div class="month">
<p><strong>JANUARY</strong></p>
</div>
<div id="musician">
<img src="melaniethumb.jpg"/>
<span>Melanie Moriss entertains with her melodic style.</span>
</div>
<div class="month">
<p><strong>FEBRUARY</strong></p>
</div>
<div id="musician">
<img src="gregthumb.jpg"/>
<span>Tahoe Greg is back from his tour. New songs. New stories.</span>
</div>
</article></br>
<div class="month">
<p><strong>UPCOMING ARTISTS</strong></p>
</div><br><br>
<table id="php">
<?php
//connecting to database
$con=mysqli_connect('localhost','root','');
//select database
mysqli_select_db($con,'musicians');
$sql='SELECT MONTHNAME(p.MonthYear),m.name,m.genre 
FROM musician AS m,performance AS p 
WHERE m.musicianid=p.musicianid 
ORDER BY p.MonthYear; ';
$records=mysqli_query($con,$sql);
echo "<tr><th>MUSICIAN</th><th>GENRE</th><th>DATE</th></tr>";
while($musicians=mysqli_fetch_assoc($records)){
echo "<tr>";
echo "<td><a style='color:blue;' href='http://localhost/artistdetails.html?searchKey=".$musicians['name']."'>".$musicians['name']."</a></td>";
echo "<td>".$musicians['genre']."</td>";
echo "<td>".$musicians['MONTHNAME(p.MonthYear)']."</td>";
echo "</tr>";
}
?>
</table><br><br>
<footer class="musicfooter"><p><i>Copyright &copy; 2016 JavaJam Coffee House</i></br>
<a href="http://www.gmail.com">vanisha@crasta.com</a></i></p></footer>
</body>
</html>