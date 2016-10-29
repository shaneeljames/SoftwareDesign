<?php

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);


$tutor_student_id = $_POST["tutor_student_id"];
$student_id = $_POST["student_id"];

//execute the SQL query and return records

 $mysql_qry =  "UPDATE TUTOR_STUDENT_TBL SET status= 'cancelled', available = 0 WHERE tutor_student_id = '$tutor_student_id' " ;
 $mysql_qry2 =  "UPDATE STUDENT_TBL SET STUDENT_TBL.student_current_balance= STUDENT_TBL.student_current_balance WHERE STUDENT_TBL.student_id = '$student_id' " ;



if($conn->query($mysql_qry) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry."<br>".$conn->error;
}

if($conn->query($mysql_qry2) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry2."<br>".$conn->error;
}

//close the connection
$conn->close();

?>