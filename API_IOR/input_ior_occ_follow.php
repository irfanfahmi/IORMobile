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


  $occ_id = $_POST["occ_id"];
  $follow_desc =  $_POST["follow_desc"];  
  $follow_by =  $_POST["follow_by"]; 
  $follow_by_name =  $_POST["follow_by_name"]; 
  $follow_by_unit =  $_POST["follow_by_unit"];
  $follow_date =  $_POST["follow_date"]; 
  $query = "INSERT INTO tbl_occ_follow(follow_by,follow_by_name,follow_by_unit,follow_date,follow_desc,occ_id) 
        VALUES('$follow_by','$follow_by_name','$follow_by_unit','$follow_date','$follow_desc','$occ_id')" or die(mysql_error());
  $result = mysqli_query($conn,$query);  

     if($query)  
     {  
         echo "Success"; 
    }  else  
    {   
     echo "Failed";  
 }  
 ?>  