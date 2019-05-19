<?php 
class SubCategory{
	private $conn;
	private $table_name = "tbl_occ_subcattegory";

	public $cat_sub_id;
	public $cat_id;
	public $cat_sub_desc;

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