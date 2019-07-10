<?php
include_once("koneksi.php");
$name = $_POST["name"];
$data = null;
  $query = "SELECT * FROM `tbl_occ` WHERE `created_by_name` LIKE '%$name%' ORDER BY occ_id ASC";
//$query = "SELECT * FROM `tbl_occ` ORDER BY occ_id ASC";
 $result = mysqli_query($db, $query); 
 while ($row = mysqli_fetch_assoc($result)) { 
  $data[] = $row; 
 } 
 echo json_encode($data);
?>