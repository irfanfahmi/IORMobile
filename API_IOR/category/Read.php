<?php 

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../config/Database.php';
include_once '../objects/Category.php';

$database = new Database();
$db = $database->getConnection();

$unit = new Category($db);

$stmt = $unit->read();
$num = $stmt->rowCount();

if($num>0){

    $category_arr=array();
    
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $category_item=array(
            "cat_id" => $cat_id,
            "cat_name" => $cat_name
        );
 
        array_push($category_arr, $category_item);
        
    }

    http_response_code(200);
    echo json_encode($category_arr);
}else{
	http_response_code(404);

    echo json_encode(
        array("message" => "No Unit found.")
    );
}
 ?>