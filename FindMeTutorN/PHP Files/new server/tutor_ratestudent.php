<?php

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);


$tutor_student_id = $_POST["tutor_student_id"];
$tutor_rating = $_POST["tutor_rating"];
$tutor_id = $_POST["tutor_id"];


//execute the SQL query and return records

 $mysql_qry = "UPDATE TUTOR_STUDENT_TBL SET status= 'Complete' AND tutor_rating = '$tutor_rating' WHERE tutor_student_id = '$tutor_student_id' " ;

 $mysql_qry1 = "UPDATE TUTOR_TBL SET TUTOR_TBL.tutor_rating = (SELECT SUM(tutor_rating)/COUNT(tutor_rating) FROM TUTOR_STUDENT_TBL  WHERE tutor_id = '$tutor_id') WHERE tutor_id = '$tutor_id" ;



if($conn->query($mysql_qry) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry."<br>".$conn->error;
}

if($conn->query($mysql_qry1) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry1."<br>".$conn->error;
}

//close the connection
$conn->close();

?>