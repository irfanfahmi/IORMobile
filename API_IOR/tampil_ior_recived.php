<?php  
define('DB_SERVER', 'localhost');
define('DB_USERNAME', 'root');
define('DB_PASSWORD', '');
define('DB_DATABASE', '190204_db_ior');

$db = mysqli_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD, DB_DATABASE);

// Check connection
if (!$db) {
    die("Connection failed: " . mysqli_connect_error());
}

 //$connection = mysqli_connect('localhost','root','','190204_db_ior'); 

 $query = "SELECT * FROM tb_occ";

 $result = mysqli_query($db, $query); 

  while ($row = mysqli_fetch_assoc($result)) { 
  $data[] = $row; 
 } 
 echo json_encode($data);
?>