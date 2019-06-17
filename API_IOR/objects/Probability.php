<?php 
class Probability{
	private $conn;
	private $table_name = "z_probability";

	public $probability_id;
	public $probability_a_definition;
	public $probability_meaning;
	public $probability_value;

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