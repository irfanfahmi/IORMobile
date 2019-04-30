<?php  
$servername = "localhost";
$username = "root";
$password = "";
$database = "190204_db_ior";
// Create connection
$conn = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$response = array();


  $username = $_POST["username"];
  $password =  $_POST["password"];  
  $query = "select id, name, unit, username from users where username = '$username' and password = '$password';";  
  $result = mysqli_query($conn,$query);  

     if(mysqli_num_rows($result) == 1)  
     {  
         $row = mysqli_fetch_assoc($result); 

         $id =$row["id"];
         $name =$row["name"];
          $unit =$row["unit"];
          $username =$row["username"];      
        echo $id.",".$name.",".$unit.",".$username;  
         

    } else  
    {   
     echo "Login Failed ";  
 }  
 ?>  