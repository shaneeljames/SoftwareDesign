<?php
//Required for login
require "conn.php";
//Variable Names...
$studentnumber = $_POST["StudentNumber"];
$password = $_POST["Password"];


//Connection to the database
//$dbhandle = mysql_connect(localhost, root, asdf) or die("Unable to connect to MySQL");
//echo "Connected to MySQL<br>";

//Select a database to work with
//$selected = mysql_select_db("findmetutor",$dbhandle) or die("Could not select findmetutor database");

//execute the SQL query and return records
$mysql_qry = "SELECT * FROM STUDENT_TBL WHERE student_student_number = '$studentnumber' and student_password = '$password'";

$rows = array();

if($conn->query($mysql_qry) === TRUE){

	while($r = mysqli_fetch_assoc($mysql_qry)) {
    	$rows[] = $r;
    }

}
else{
echo "Insert Unsuccessful".$mysql_qry."<br>".$conn->error;
}

print json_encode($rows);

//close the connection
$conn->close();
?>



