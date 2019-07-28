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

function mime2ext($mime){
    $all_mimes = '{"png":["image\/png","image\/x-png"],"bmp":["image\/bmp","image\/x-bmp",
    "image\/x-bitmap","image\/x-xbitmap","image\/x-win-bitmap","image\/x-windows-bmp",
    "image\/ms-bmp","image\/x-ms-bmp","application\/bmp","application\/x-bmp",
    "application\/x-win-bitmap"],"gif":["image\/gif"],"jpeg":["image\/jpeg",
    "image\/pjpeg"],"xspf":["application\/xspf+xml"],"vlc":["application\/videolan"],
    "wmv":["video\/x-ms-wmv","video\/x-ms-asf"],"au":["audio\/x-au"],
    "ac3":["audio\/ac3"],"flac":["audio\/x-flac"],"ogg":["audio\/ogg",
    "video\/ogg","application\/ogg"],"kmz":["application\/vnd.google-earth.kmz"],
    "kml":["application\/vnd.google-earth.kml+xml"],"rtx":["text\/richtext"],
    "rtf":["text\/rtf"],"jar":["application\/java-archive","application\/x-java-application",
    "application\/x-jar"],"zip":["application\/x-zip","application\/zip",
    "application\/x-zip-compressed","application\/s-compressed","multipart\/x-zip"],
    "7zip":["application\/x-compressed"],"xml":["application\/xml","text\/xml"],
    "svg":["image\/svg+xml"],"3g2":["video\/3gpp2"],"3gp":["video\/3gp","video\/3gpp"],
    "mp4":["video\/mp4"],"m4a":["audio\/x-m4a"],"f4v":["video\/x-f4v"],"flv":["video\/x-flv"],
    "webm":["video\/webm"],"aac":["audio\/x-acc"],"m4u":["application\/vnd.mpegurl"],
    "pdf":["application\/pdf","application\/octet-stream"],
    "pptx":["application\/vnd.openxmlformats-officedocument.presentationml.presentation"],
    "ppt":["application\/powerpoint","application\/vnd.ms-powerpoint","application\/vnd.ms-office",
    "application\/msword"],"docx":["application\/vnd.openxmlformats-officedocument.wordprocessingml.document"],
    "xlsx":["application\/vnd.openxmlformats-officedocument.spreadsheetml.sheet","application\/vnd.ms-excel"],
    "xl":["application\/excel"],"xls":["application\/msexcel","application\/x-msexcel","application\/x-ms-excel",
    "application\/x-excel","application\/x-dos_ms_excel","application\/xls","application\/x-xls"],
    "xsl":["text\/xsl"],"mpeg":["video\/mpeg"],"mov":["video\/quicktime"],"avi":["video\/x-msvideo",
    "video\/msvideo","video\/avi","application\/x-troff-msvideo"],"movie":["video\/x-sgi-movie"],
    "log":["text\/x-log"],"txt":["text\/plain"],"css":["text\/css"],"html":["text\/html"],
    "wav":["audio\/x-wav","audio\/wave","audio\/wav"],"xhtml":["application\/xhtml+xml"],
    "tar":["application\/x-tar"],"tgz":["application\/x-gzip-compressed"],"psd":["application\/x-photoshop",
    "image\/vnd.adobe.photoshop"],"exe":["application\/x-msdownload"],"js":["application\/x-javascript"],
    "mp3":["audio\/mpeg","audio\/mpg","audio\/mpeg3","audio\/mp3"],"rar":["application\/x-rar","application\/rar",
    "application\/x-rar-compressed"],"gzip":["application\/x-gzip"],"hqx":["application\/mac-binhex40",
    "application\/mac-binhex","application\/x-binhex40","application\/x-mac-binhex40"],
    "cpt":["application\/mac-compactpro"],"bin":["application\/macbinary","application\/mac-binary",
    "application\/x-binary","application\/x-macbinary"],"oda":["application\/oda"],
    "ai":["application\/postscript"],"smil":["application\/smil"],"mif":["application\/vnd.mif"],
    "wbxml":["application\/wbxml"],"wmlc":["application\/wmlc"],"dcr":["application\/x-director"],
    "dvi":["application\/x-dvi"],"gtar":["application\/x-gtar"],"php":["application\/x-httpd-php",
    "application\/php","application\/x-php","text\/php","text\/x-php","application\/x-httpd-php-source"],
    "swf":["application\/x-shockwave-flash"],"sit":["application\/x-stuffit"],"z":["application\/x-compress"],
    "mid":["audio\/midi"],"aif":["audio\/x-aiff","audio\/aiff"],"ram":["audio\/x-pn-realaudio"],
    "rpm":["audio\/x-pn-realaudio-plugin"],"ra":["audio\/x-realaudio"],"rv":["video\/vnd.rn-realvideo"],
    "jp2":["image\/jp2","video\/mj2","image\/jpx","image\/jpm"],"tiff":["image\/tiff"],
    "eml":["message\/rfc822"],"pem":["application\/x-x509-user-cert","application\/x-pem-file"],
    "p10":["application\/x-pkcs10","application\/pkcs10"],"p12":["application\/x-pkcs12"],
    "p7a":["application\/x-pkcs7-signature"],"p7c":["application\/pkcs7-mime","application\/x-pkcs7-mime"],"p7r":["application\/x-pkcs7-certreqresp"],"p7s":["application\/pkcs7-signature"],"crt":["application\/x-x509-ca-cert","application\/pkix-cert"],"crl":["application\/pkix-crl","application\/pkcs-crl"],"pgp":["application\/pgp"],"gpg":["application\/gpg-keys"],"rsa":["application\/x-pkcs7"],"ics":["text\/calendar"],"zsh":["text\/x-scriptzsh"],"cdr":["application\/cdr","application\/coreldraw","application\/x-cdr","application\/x-coreldraw","image\/cdr","image\/x-cdr","zz-application\/zz-winassoc-cdr"],"wma":["audio\/x-ms-wma"],"vcf":["text\/x-vcard"],"srt":["text\/srt"],"vtt":["text\/vtt"],"ico":["image\/x-icon","image\/x-ico","image\/vnd.microsoft.icon"],"csv":["text\/x-comma-separated-values","text\/comma-separated-values","application\/vnd.msexcel"],"json":["application\/json","text\/json"]}';
    $all_mimes = json_decode($all_mimes,true);
    foreach ($all_mimes as $key => $value) {
        if(array_search($mime,$value) !== false) return $key;
    }
    return false;
}

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

    $upload_folder = "../attachment/";
    $decode_file = base64_decode($data->attachment);
    $mime_type = finfo_buffer(finfo_open(), $decode_file, FILEINFO_MIME_TYPE);
    $extension = mime2ext($mime_type);
    $file = uniqid() .'.'. $extension;
    $file_dir = $upload_folder . uniqid() .'.'. $extension;
    $move_file = file_put_contents($file_dir, $decode_file);

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
        $occ->attachment = $file;
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