<?php 

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../config/Database.php';
include_once '../objects/Severity.php';

$database = new Database();
$db = $database->getConnection();

$severity = new Severity($db);

$stmt = $severity->read();
$num = $stmt->rowCount();

if($num>0){

    $severity_arr=array();
 

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $severity_item=array(
            "severity_id" => $severity_id,
            "severity_q_definition" => $severity_q_definition,
            "severity_meaning" => $severity_meaning,
            "severity_value" => $severity_value
        );
 
        array_push($severity_arr, $severity_item);
    }

    http_response_code(200);

    echo json_encode($severity_arr);
}else{
	http_response_code(404);

    echo json_encode(
        array("message" => "No Unit found.")
    );
}
 ?>
