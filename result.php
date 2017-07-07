<!doctype html>
<head>
<title>Khalid Center</title>
<link rel="stylesheet" href="style.css"/>
</head>
<body>
<header>KHALID CENTER</header>
<nav>
<a href="home.html">Home</a>
<a href="services.html">Services</a>
<a href="request.html">Place a request</a>
<a href="contact.html">Contact</a>
</nav><br><br>
<article>
<?php
//connecting to database
$con=mysqli_connect('localhost','root','');
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
//select database
mysqli_select_db($con,'khalid_center');
$name=$_POST['name'];
$phone=$_POST['phone'];
$address=$_POST['address'];
$servicedate=$_POST['servicedate'];
$service=$_POST['service'];
$value="";
foreach($service as $value1)  
   {  
      $value .= $value1.",";  
   } 
 
$sql="INSERT INTO servicerequests(Name,Telephone,Service,Address,Service_Date) VALUES ('$name','$phone','$value','$address','$servicedate');";
$result =mysqli_query($con,$sql);
if (!$result) {
    echo "sorry!..your request could not be placed";
}
else
{
	echo "<h5>".$name." , your request has been submitted </h5>";
}
?>
</article><br><br>
<footer>
<p><i>Copywrite &copy; 2014 Khalid Center</p>
</footer>
</body>
</html>