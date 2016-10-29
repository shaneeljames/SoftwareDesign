<?php
//Required for login
require "conn.php";
//Variable Names...
$tutor_id = $_POST["tutor_id"];

$subject_id = $_POST["subject_id"];



if($conn==""){
echo "Incorrect Insert";
$conn->close();
}
else{


$mysql_qry = "INSERT INTO TUTOR_SUBJECT_TBL (subject_id,tutor_id) VALUES ('$subject_id','$tutor_id')";

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



