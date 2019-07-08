<?php
include_once("koneksi.php");

$occ_id = $_POST["occ_id"];
$data =null;
$query = "SELECT * FROM `tbl_occ_follow` WHERE occ_id = $occ_id";
 $result = mysqli_query($db, $query); 
 

 if(mysqli_num_rows($result) >=1)  
     {
     	while ($row = mysqli_fetch_assoc($result)) {
        	$data[] = $row;
        }
        echo json_encode($data);
    } else{
    	echo "Tidak Ada Data";
    } 


?>