<?php
//Required for login
require "conn.php";
//Variable Names...
$tutor_id = $_POST["TutorID"];
$student_id = $_POST["StudentID"];
$subject_id = $_POST["SubjectID"];
$amount = $_POST["Amount"];
$date = $_POST["Date"];
$time = $_POST["Time"];
$description = $_POST["Description"];


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
$mysql_qry = "INSERT INTO TUTOR_STUDENT_TBL (tutor_id,student_id,subject_id,amount,date,time,description) VALUES ('$tutor_id','$student_id,'$subject_id','$amount','$date','$time','$description')";

if($conn->query($mysql_qry) === TRUE){
echo "Session Requested";
}
else{
echo "Unsuccessful".$mysql_qry."<br>".$conn->error;
}

//close the connection
$conn->close();
}

?>



