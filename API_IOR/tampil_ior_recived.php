<?php
include_once("koneksi.php");
$unit = $_POST["unit"];
$query = "SELECT * FROM `tbl_occ` WHERE `occ_send_to` LIKE '%$unit%' ORDER BY occ_id ASC";
//$query = "SELECT * FROM `tbl_occ` ORDER BY occ_id ASC";
 $result = mysqli_query($db, $query); 
 while ($row = mysqli_fetch_assoc($result)) { 
  $data[] = $row; 
 } 
 echo json_encode($data);
?>