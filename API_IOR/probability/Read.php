<?php 

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../config/Database.php';
include_once '../objects/Probability.php';

$database = new Database();
$db = $database->getConnection();

$probability = new Probability($db);

$stmt = $probability->read();
$num = $stmt->rowCount();

if($num>0){

    $probability_arr=array();
 

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $probability_item=array(
            "probability_id" => $probability_id,
            "probability_a_definition" => $probability_a_definition,
            "probability_meaning" => $probability_meaning,
            "probability_value" => $probability_value
        );
 
        array_push($probability_arr, $probability_item);
    }

    http_response_code(200);

    echo json_encode($probability_arr);
}else{
	http_response_code(404);

    echo json_encode(
        array("message" => "No Unit found.")
    );
}
 ?>
