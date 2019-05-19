<?php
class Unit{

	private $conn;
	private $table_name = "tbl_unit";

	public $id;
	public $unit;
	public $group;

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