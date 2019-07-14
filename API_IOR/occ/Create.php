<?php 
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once '../config/Database.php';
include_once '../objects/OCC.php';
$database = new Database();
$db = $database->getConnection();
$con = mysqli_connect("localhost","root","","190204_db_ior");
$occ = new OCC($db);
$data = json_decode(file_get_contents("php://input"));
$occ_resp=array();
$occ_resp["check"]=array();
$occ_resp["message"]=array();
$occ_resp["code"]=array();
if(
    !empty($data->occ_send_to)&&
    !empty($data->occ_sub)&&
    !empty($data->occ_category)&&
    !empty($data->occ_sub_category)&&
    !empty($data->occ_sub_spec)&&
    !empty($data->occ_ambiguity)&&
    !empty($data->occ_date)&&
    !empty($data->estfinish)&&
    !empty($data->attachment)&&
    !empty($data->occ_level_type)&&
    !empty($data->occ_risk_index)&&
    !empty($data->occ_detail)&&
    !empty($data->created_by)&&
    !empty($data->created_by_name)&&
    !empty($data->created_by_unit)
    ){
    array_push($occ_resp["check"],"data not empty");
    $now = DateTime::createFromFormat('U.u', microtime(true));
    $query = "SELECT * FROM tbl_occ WHERE MONTH(created_date) = MONTH(CURRENT_DATE)";
    $report_in_month = mysqli_query($con, $query); 
    $report_now = mysqli_num_rows($report_in_month) + 1;
    $part_no_occ = $now->format('m/Y');
    $no_occ = sprintf("%03d",$report_now)."/".$part_no_occ;
    $id = $now->format('YmdHisu');
    $current_time = $now->format('Y-m-d H:i:s');
    $upload_folder = "attachment";
    $name_file = $id.".jpeg";
    $path = "../".$upload_folder."/".$name_file;
    $move_file = file_put_contents($path, base64_decode($data->attachment));
    $status = "0"; //status open default 1
    
    if($move_file){
        $occ->occ_no = $no_occ;
        $occ->occ_send_to = $data->occ_send_to;
        $occ->occ_sub = $data->occ_sub;
        $occ->occ_category = $data->occ_category;
        $occ->occ_sub_category = $data->occ_sub_category;
        $occ->occ_sub_spec = $data->occ_sub_spec;
        $occ->occ_ambiguity = $data->occ_ambiguity;
        $occ->occ_date = $data->occ_date;
        $occ->estfinish = $data->estfinish;
        $occ->attachment = $name_file;
        $occ->occ_level_type = $data->occ_level_type;
        $occ->occ_risk_index = $data->occ_risk_index;
        $occ->occ_detail = $data->occ_detail;
        $occ->occ_status = $status;
        $occ->created_date = $current_time;
        $occ->created_by = $data->created_by;
        $occ->created_by_name = $data->created_by_name;
        $occ->created_by_unit = $data->created_by_unit;
        $occ->created_hide = $data->created_hide;
        if($occ->create()){
            http_response_code(201);
            array_push($occ_resp["message"],"OCC was created.");
            array_push($occ_resp["code"],"201");
            echo json_encode($occ_resp);
        } else{
            http_response_code(503);
            array_push($occ_resp["message"],"Unable to create OCC.");
            array_push($occ_resp["code"],"503");
            echo json_encode($occ_resp);
        }
    }
    
}else{
    http_response_code(400);
    array_push($occ_resp["check"],"some data is null");
    array_push($occ_resp["message"],"Unable to create OCC. Data is incomplete.");
    array_push($occ_resp["code"],"400");
    echo json_encode($occ_resp);
}
?>