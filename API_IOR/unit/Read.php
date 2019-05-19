<?php 

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../config/Database.php';
include_once '../objects/Unit.php';

$database = new Database();
$db = $database->getConnection();

$unit = new Unit($db);

$stmt = $unit->read();
$num = $stmt->rowCount();

if($num>0){

    $unit_arr=array();
 

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $unit_item=array(
            "id" => $id,
            "unit" => $unit,
            "group" => $group
        );
 
        array_push($unit_arr, $unit_item);
    }

    http_response_code(200);

    echo json_encode($unit_arr);
}else{
	http_response_code(404);

    echo json_encode(
        array("message" => "No Unit found.")
    );
}
 ?>
