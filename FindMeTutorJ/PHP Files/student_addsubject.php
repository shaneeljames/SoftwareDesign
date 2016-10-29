<?php
//Required for login
require "conn.php";
//Variable Names...
$studentid = $_POST["StudentID"];

$subid = $_POST["SubjectID"];



if($conn==""){
echo "Incorrect Insert";
$conn->close();
}
else{

//Connection to the database
//$dbhandle = mysql_connect(localhost, root, asdf) or die("Unable to connect to MySQL");
//echo "Connected to MySQL<br>";

//Select a database to work with
//$selected = mysql_select_db("findmetutor",$dbhandle) or die("Could not select findmetutor database");

//execute the SQL query and return records
$mysql_qry = "INSERT INTO STUDENT_SUBJECT_TBL (subject_id,student_id) VALUES ('$subid','$studentid')";

if($conn->query($mysql_qry) === TRUE){
echo "Insert Successful";
}
else{
echo "Insert Unsuccessful".$mysql_qry."<br>".$conn->error;
}

//close the connection
$conn->close();
}

?>



