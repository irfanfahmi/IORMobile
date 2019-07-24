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




 $ekstensi_diperbolehkan  = array('png','jpg');
  //$image = $_FILES['gambar_kuliner']['name'];
  //$x = explode('.', $image);
  //$ekstensi = strtolower(end($x));
 // $ukuran = $_FILES['gambar_kuliner']['size'];
  

   $decodegambar = base64_decode($foto_report);
   //$file_tmp = base64_decode($_FILES['gambar_kuliner']['tmp_name']) ;  
    $dir = 'attachment/'.$sub_lapor.".jpg";

    file_put_contents($dir,$decodegambar);
    $now = DateTime::createFromFormat('U.u', microtime(true));
    $query1 = "SELECT * FROM tbl_occ WHERE MONTH(created_date) = MONTH(CURRENT_DATE)";
    $part_no_occ = $now->format('m/Y');
    $report_in_month = mysqli_query($db, $query1); 
    $report_now = mysqli_num_rows($report_in_month) + 1;
    $no_occ = sprintf("%03d",$report_now)."/".$part_no_occ;
    $status = "0"; //status open default 1
  
  //file_put_contents($pathImage, $dataBase64);

       /// move_uploaded_file($file_tmp,$dir);
        
        $query = "INSERT INTO tbl_occ(occ_status,occ_no,occ_sub,occ_detail,occ_reff,ReportedDate,created_hide, attachment, created_by_name, created_by_unit) 
        VALUES('$status','$no_occ','$sub_lapor','$des_lapor','$ref_lapor','$date_lapor','$porter_lapor','$dir','$nama_guest','$unit_guest')" or die(mysql_error());
        $sql = mysqli_query($db, $query);
       
     if($query)  
     {  
         echo "Report Success"; 
    }  else  
    {   
     echo "Report failed";  
 }  

 }
 ?>   