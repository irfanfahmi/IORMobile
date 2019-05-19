<?php 
class SubCategorySpec{
	private $conn;
	private $table_name = "tbl_occ_sub_catt_spec";

	public $cat_sub_spec_id;
	public $cat_sub_id;
	public $cat_sub_spec_desc;

	public function __construct($db){
        $this->conn = $db;
    }

    function read(){
    	$query = "SELECT * FROM " . $this->table_name . "";
	    $stmt = $this->conn->prepare($query);
	    $stmt->execute();
	 
	    return $stmt;
    }

}


 ?>