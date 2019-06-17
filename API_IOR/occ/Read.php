<?php 

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../config/Database.php';
include_once '../objects/OCC.php';

$database = new Database();
$db = $database->getConnection();

$occ = new OCC($db);

$stmt = $occ->read();
$num = $stmt->rowCount();

if($num>0){

    $occ_arr=array();
 

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $occ_item=array(
            "occ_id" => $occ_id,
            "occ_no" => $occ_no,
            "occ_sub" => $occ_sub,
            "occ_detail" => $occ_detail,
            "occ_risk_index" => $occ_risk_index,
            "occ_probability" => $occ_probability,
            "occ_severity" => $occ_severity,
            "occ_reff" => $occ_reff,
            "occ_ambiguity" => $occ_ambiguity,
            "occ_date" => $occ_date,
            "occ_estfinish_date" => $occ_estfinish_date,
            "occ_response_date" => $occ_response_date,
            "occ_category" => $occ_category,
            "occ_sub_category" => $occ_sub_category,
            "occ_sub_spec" => $occ_sub_spec,
            "occ_level_type" => $occ_level_type,
            "occ_level_sub" => $occ_level_sub,
            "occ_level_sub_child" => $occ_level_sub_child,
            "occ_status" => $occ_status,
            "occ_confirm" => $occ_confirm,
            "occ_send_to" => $occ_send_to,
            "created_date" => $created_date,
            "created_by" => $created_by,
            "created_by_name" => $created_by_name,
            "created_by_unit1" => $created_by_unit1,
            "created_hide" => $created_hide,
            "permission" => $permission,
            "occ_follow_last_by" => $occ_follow_last_by,
            "ReportedDate" => $ReportedDate,
            "attachment" => $attachment,
            "InsertBy" => $InsertBy,
            "hazard" => $hazard,
            "estfinish" => $estfinish,

        );
 
        array_push($occ_arr, $occ_item);
    }

    http_response_code(200);

    echo json_encode($occ_arr);
}else{
	http_response_code(404);

    echo json_encode(
        array("message" => "No Unit found.")
    );
}
 ?>
