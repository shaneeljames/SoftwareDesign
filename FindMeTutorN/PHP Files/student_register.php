<?php
//Required for login
require "conn.php";
//Variable Names...
$name = $_POST["Name"];
$surname = $_POST["Surname"];
$contactnumber = $_POST["Contactnumber"];
$studentnumber = $_POST["StudentNumber"];
$email = $_POST["Email"];
$password = $_POST["Password"];


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
$mysql_qry = "INSERT INTO STUDENT_TBL (student_student_number,student_fname,student_lname,student_contact_num,student_email,student_password) VALUES ('$studentnumber','$name,'$surname','$contactnumber','$email','$password')";

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



