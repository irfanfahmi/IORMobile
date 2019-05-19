<?php
class Category{
	private $conn;
	private $table_name = "tbl_occ_cattegory";

	public $cat_id;
	public $cat_name;

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