<?php
//Required for login
$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

//Variable Names...

$id = $_POST["TutorID"];
$ses_id = $_POST["SessionID"];


//Connection to the database
//$dbhandle = mysql_connect(localhost, root, asdf) or die("Unable to connect to MySQL");
//echo "Connected to MySQL<br>";

//Select a database to work with
//$selected = mysql_select_db("findmetutor",$dbhandle) or die("Could not select findmetutor database");

//execute the SQL query and return records
$mysql_qry = mysqli_query( $conn, "UPDATE TUTOR_CONFIRMED_TBL SET tutor_id = '$id' , status = "Confirmed" WHERE tutor_student_id='$sess_id'");

$rows = array();

if($conn->query($mysql_qry) === TRUE){

	echo "Update Sucessful";

}
else{
echo "Update Unsuccessful".$mysql_qry."<br>".$conn->error;
}


//close the connection
$conn->close();
?>



