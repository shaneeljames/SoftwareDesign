<?php

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$tutor_student_id = $_POST["tutor_student_id"];
$tutor_checkin = $_POST["tutor_checkin"] ;

//execute the SQL query and return records


 $mysql_qry1 = "UPDATE TUTOR_STUDENT_TBL SET tutor_checkout = '$tutor_checkin' WHERE tutor_student_id = '$tutor_student_id'" ;



if($conn->query($mysql_qry1) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry1."<br>".$conn->error;
}

//close the connection
$conn->close();

?>