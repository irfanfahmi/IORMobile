<?php 
require_once 'koneksi.php';
$name = $_POST["name"];
$type="users";
if (isset($_POST['key'])) {
    $key = $_POST["key"];
 if ($type == 'users') {
        $query = "SELECT * FROM `tbl_occ` WHERE `created_by_name` LIKE '%$name%' ORDER BY `occ_date` DESC ";
        $result = mysqli_query($db, $query);
        // print_r($result);
        // exit();
        $response = array();
        while($row = mysqli_fetch_assoc($result)){
            array_push($response, 
            array(
                'occ_id'=>$row['occ_id'], 
                'occ_no'=>$row['occ_no'], 
                'occ_sub'=>$row['occ_sub'],
                'occ_detail'=>$row['occ_detail'],
                 'occ_date'=>$row['occ_date'],
                 'occ_risk_index'=>$row['occ_risk_index'],
                'occ_reff'=>$row['occ_reff'],
                'occ_category'=>$row['occ_category'],
                'occ_sub_category'=>$row['occ_sub_category'],
                'occ_send_to'=>$row['occ_send_to'],
                'occ_estfinish'=>$row['estfinish'],
                'occ_Insertby'=>$row['InsertBy'],
                'created_by_name'=>$row['created_by_name'],
                'occ_status'=>$row['occ_status'],
                'created_by_unit'=>$row['created_by_unit'],
                'ReportedDate'=>$row['ReportedDate'],
                'attachment'=>$row['attachment'])



            );
        }
        echo json_encode($response);   
     }
} else {
   if ($type == 'users') {
        $query = "SELECT * FROM `tbl_occ` WHERE `created_by_name` LIKE '%$name%' ORDER BY `occ_date` DESC ";
        $result = mysqli_query($db, $query);
        $response = array();
        while( $row = mysqli_fetch_assoc($result) ){
            array_push($response, 
            array(
                 'occ_id'=>$row['occ_id'], 
                'occ_no'=>$row['occ_no'], 
                'occ_sub'=>$row['occ_sub'],
                'occ_detail'=>$row['occ_detail'],
                 'occ_date'=>$row['occ_date'],
                 'occ_risk_index'=>$row['occ_risk_index'],
                'occ_reff'=>$row['occ_reff'],
                'occ_category'=>$row['occ_category'],
                'occ_sub_category'=>$row['occ_sub_category'],
                'occ_send_to'=>$row['occ_send_to'],
                'occ_estfinish'=>$row['estfinish'],
                'occ_Insertby'=>$row['InsertBy'],
                'created_by_name'=>$row['created_by_name'],
                'occ_status'=>$row['occ_status'],
                'created_by_unit'=>$row['created_by_unit'],
                'ReportedDate'=>$row['ReportedDate'],
                'attachment'=>$row['attachment'])
            );
        }
        echo json_encode($response);   
    }
}

mysqli_close($db);

?>