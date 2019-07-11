<?php
include_once("koneksi.php");
$occ_status = $_POST["occ_status"];
$occ_id = $_POST["occ_id"];
$query = " UPDATE `tbl_occ` SET `occ_status`='$occ_status' WHERE `occ_id`='$occ_id'";
//$query = "SELECT * FROM `tbl_occ` ORDER BY occ_id ASC";
 $result = mysqli_query($db, $query); 

	if($result){
		echo "Success";

	} else{
		echo "Failed";
	}  

?>