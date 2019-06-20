<?php
class OCC{
	private $conn;
	private $table_name = "tbl_occ";

	public $occ_id;
	public $occ_no;
	public $occ_sub;
	public $occ_detail;
	public $occ_risk_index;
	public $occ_probability;
	public $occ_severity;
	public $occ_reff;
	public $occ_ambiguity;
	public $occ_date;
	public $occ_estfinish_date;
	public $occ_response_date;
	public $occ_category;
	public $occ_sub_category;
	public $occ_sub_spec;
	public $occ_level_type;
	public $occ_level_sub;
	public $occ_level_sub_child;
	public $occ_status;
	public $occ_confirm_stats;
	public $occ_send_to;
	public $created_date;
	public $create_by;
	public $create_by_name;
	public $create_by_unit;
	public $create_hide;
	public $permission;
	public $occ_follow_last_by;
	public $ReportedDate;
	public $attachment;
	public $insertBy;
	public $hazard;
	public $estfinish;


	public function __construct($db){
        $this->conn = $db;
    }

    function read(){
    	$query = "SELECT * FROM " . $this->table_name . "";
	    $stmt = $this->conn->prepare($query);
	    $stmt->execute();
	 
	    return $stmt;
    }
    function create(){

    	$query = "INSERT INTO tbl_occ (
    	occ_send_to, 
    	occ_sub, 
    	occ_category, 
    	occ_sub_category, 
    	occ_sub_spec, 
    	occ_ambiguity, 
    	occ_date, 
    	estfinish, 
    	attachment, 
    	occ_level_type, 
    	occ_risk_index, 
    	occ_detail,
    	created_date,
    	created_by,
    	create_by_name,
    	create_by_unit) VALUES (
    	:occ_send_to,
    	:occ_sub,
    	:occ_category,
    	:occ_sub_category,
    	:occ_sub_spec,
    	:occ_ambiguity,
    	:occ_date,
    	:estfinish,
    	:attachment,
    	:occ_level_type,
    	:occ_risk_index,
    	:occ_detail,
    	:created_date,
    	:created_by,
    	:created_by_name,
    	:created_by_unit)";

        $stmt = $this->conn->prepare($query);

	    $this->occ_send_to=htmlspecialchars(strip_tags($this->occ_send_to));
	    $this->occ_sub=htmlspecialchars(strip_tags($this->occ_sub));
	    $this->occ_category=htmlspecialchars(strip_tags($this->occ_category));
	    $this->occ_sub_category=htmlspecialchars(strip_tags($this->occ_sub_category));
	    $this->occ_sub_spec=htmlspecialchars(strip_tags($this->occ_sub_spec));
	    $this->occ_ambiguity=htmlspecialchars(strip_tags($this->occ_ambiguity));
	    $this->occ_date=htmlspecialchars(strip_tags($this->occ_date));
	    $this->estfinish=htmlspecialchars(strip_tags($this->estfinish));
	    $this->attachment=htmlspecialchars(strip_tags($this->attachment));
	    $this->occ_level_type=htmlspecialchars(strip_tags($this->occ_level_type));
	    $this->occ_risk_index=htmlspecialchars(strip_tags($this->occ_risk_index));
	    $this->occ_detail=htmlspecialchars(strip_tags($this->occ_detail));
	    $this->created_date=htmlspecialchars(strip_tags($this->created_date));
	    $this->created_by=htmlspecialchars(strip_tags($this->created_by));
	    $this->created_by_name=htmlspecialchars(strip_tags($this->created_by_name));
	    $this->created_by_unit=htmlspecialchars(strip_tags($this->created_by_unit));

	    $stmt->bindParam(":occ_send_to", $this->occ_send_to);
	    $stmt->bindParam(":occ_sub", $this->occ_sub);
	    $stmt->bindParam(":occ_category", $this->occ_category);
	    $stmt->bindParam(":occ_sub_category", $this->occ_sub_category);
	    $stmt->bindParam(":occ_sub_spec", $this->occ_sub_spec);
	    $stmt->bindParam(":occ_ambiguity", $this->occ_ambiguity);
	    $stmt->bindParam(":occ_date", $this->occ_date);
	    $stmt->bindParam(":estfinish", $this->estfinish);
	    $stmt->bindParam(":attachment", $this->attachment);
	    $stmt->bindParam(":occ_level_type", $this->occ_level_type);
	    $stmt->bindParam(":occ_risk_index", $this->occ_risk_index);
	    $stmt->bindParam(":occ_detail", $this->occ_detail);
	    $stmt->bindParam(":created_date", $this->created_date);
	    $stmt->bindParam(":created_by", $this->created_by);
	    $stmt->bindParam(":created_by_name", $this->created_by_name);
	    $stmt->bindParam(":created_by_unit", $this->created_by_unit);


	    if($stmt->execute()){
	        return true;
	    }
	    return false;
    }
}
 ?>