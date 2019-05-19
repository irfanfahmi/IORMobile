<?php 

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../config/Database.php';
include_once '../objects/SubCategorySpec.php';

$database = new Database();
$db = $database->getConnection();

$subCategorySpec = new SubCategorySpec($db);

$stmt = $subCategorySpec->read();
$num = $stmt->rowCount();

if($num>0){

    $sub_category_spec_arr=array();
    
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
 
        $sub_category_spec_item=array(
            "cat_sub_spec_id" => $cat_sub_spec_id,
            "cat_sub_id" => $cat_sub_id,
            "cat_sub_spec_desc" => $cat_sub_spec_desc
        );
 
        array_push($sub_category_spec_arr, $sub_category_spec_item);
        
    }

    http_response_code(200);
    echo json_encode($sub_category_spec_arr);
}else{
	http_response_code(404);

    echo json_encode(
        array("message" => "No Unit found.")
    );
}
 ?>