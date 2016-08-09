<?php
//Required for login
require "conn.php";
//Variable Names...
$name = $_POST["Name"];
$surname = $_POST["Surname"];
$contactnumber = $_POST["Contactnumber"];
$email = $_POST["Email"];
$password = $_POST["Password"];
$securityquestion = $_POST["Securityquestion"];
$answer = $_POST["Answer"];
$tutorstudent = $_POST["Tutorstudent"];

if($conn==""){
echo "Incorrect Insert";
$conn->close();
}else{

//Connection to the database
//$dbhandle = mysql_connect(localhost, root, asdf) or die("Unable to connect to MySQL");
//echo "Connected to MySQL<br>";

//Select a database to work with
//$selected = mysql_select_db("findmetutor",$dbhandle) or die("Could not select findmetutor database");

//execute the SQL query and return records
$mysql_qry = "Insert into PERSON (first_name,last_name,contact_number,email,password,security_question,answer,tutor_student) Values ('$name','$surname','$contactnumber','$email','$password','$securityquestion','$answer','$tutorstudent')";

if($conn->query($mysql_qry) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry."<br>".$conn->error;
}

//close the connection
$conn->close();
}
?>



