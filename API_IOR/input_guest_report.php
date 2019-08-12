<?php  

define('DB_SERVER', 'localhost');
define('DB_USERNAME', 'root');
define('DB_PASSWORD', '');
define('DB_DATABASE', '190204_db_ior');

$db = mysqli_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD, DB_DATABASE);

// Check connection
if (!$db) {
    die("Connection failed: " . mysqli_connect_error());
}


$response = array();


if(isset($_POST['nama_guest'])){

  $nama_guest = $_POST["nama_guest"];
  $email_guest = $_POST["email_guest"];
  $company_guest = $_POST["company_guest"];
  $unit_guest = $_POST["unit_guest"];
  $sub_lapor = $_POST["sub_lapor"];
  $ref_lapor =  $_POST["ref_lapor"]; 
  $date_lapor =  $_POST["date_lapor"]; 
  $des_lapor =  $_POST["des_lapor"]; 
  $porter_lapor =  $_POST["porter_lapor"]; 
  $foto_report =  $_POST["foto_report"]; 
  reg($_POST['nama_guest']);

}
else{
    echo "failed";
    exit;
}

function reg($nama_guest){

$db = mysqli_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD, DB_DATABASE);

// Check connection
if (!$db) {
    die("Connection failed: " . mysqli_connect_error());
}



  $nama_guest = $_POST["nama_guest"];
  $email_guest = $_POST["email_guest"];
  $company_guest = $_POST["company_guest"];
  $unit_guest = $_POST["unit_guest"];
  $sub_lapor = $_POST["sub_lapor"];
  $ref_lapor =  $_POST["ref_lapor"]; 
  $date_lapor =  $_POST["date_lapor"]; 
  $des_lapor =  $_POST["des_lapor"]; 
  $porter_lapor =  $_POST["porter_lapor"]; 
  $foto_report =  $_POST["foto_report"]; 




  //$ekstensi_diperbolehkan  = array('png','jpg');
  //$image = $_FILES['gambar_kuliner']['name'];
  //$x = explode('.', $image);
  //$ekstensi = strtolower(end($x));
  // $ukuran = $_FILES['gambar_kuliner']['size'];
  


    //$decodegambar = base64_decode($foto_report);
    //$file_tmp = base64_decode($_FILES['gambar_kuliner']['tmp_name']) ;  
    //$dir = 'attachment/'.$sub_lapor.".jpg";
    //$potofile = $sub_lapor.".jpg";
    //file_put_contents($dir,$decodegambar);
    $now = DateTime::createFromFormat('U.u', microtime(true));
    $id = $now->format('YmdHisu');
    $upload_folder = "../attachment/";
    $decode_file = base64_decode($foto_report);
    $mime_type = finfo_buffer(finfo_open(), $decode_file, FILEINFO_MIME_TYPE);
    $extension = mime2ext($mime_type);
    $file = "";
    $file_dir = $upload_folder . $id .'.'. $extension;

    if(!empty($data->attachment)){
        $file = $id .'.'. $extension;
        file_put_contents($file_dir, $decode_file);
    }

    $now = DateTime::createFromFormat('U.u', microtime(true));
    $query1 = "SELECT * FROM tbl_occ WHERE MONTH(created_date) = MONTH(CURRENT_DATE)";
    $part_no_occ = $now->format('m/Y');
    $report_in_month = mysqli_query($db, $query1); 
    $report_now = mysqli_num_rows($report_in_month) + 1;
    $no_occ = sprintf("%03d",$report_now)."/".$part_no_occ;
    $status = "0"; //status open default 1
  	$porter_lapor="0";
  	$insertByGuest = $nama_guest."/".$unit_guest;
  	$current_time = $now->format('Y-m-d H:i:s');
  	
        $query = "INSERT INTO tbl_occ(occ_status,occ_no,occ_sub,occ_detail,occ_reff,ReportedDate,created_hide, attachment, insertBy, created_by_unit, created_date, occ_date) 
        VALUES('$status','$no_occ','$sub_lapor','$des_lapor','$ref_lapor','$date_lapor','$porter_lapor','$file','$insertByGuest','$unit_guest','$current_time','$current_time')" or die(mysql_error());
        $sql = mysqli_query($db, $query);
      
     if($query==true)  
     {  
         echo "Report Success"; 
    }  else  
    {   
     echo "Report failed";  
 }  

 }

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
 ?>   