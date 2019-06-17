<?php 
class Severity{
	private $conn;
	private $table_name = "z_severity";

	public $severity_id;
	public $severity_q_definition;
	public $severity_meaning;
	public $severity_value;

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