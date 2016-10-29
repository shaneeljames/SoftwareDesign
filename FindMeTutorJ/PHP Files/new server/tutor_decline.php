<?php

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);


$tutor_id = $_POST["tutor_id"];
$tutor_student_id = $_POST["tutor_student_id"];
$student_id = $_POST["student_id"] ;

//execute the SQL query and return records


 $mysql_qry1 = "INSERT INTO TUTOR_CONFIRMED_TBL (tutor_student_id,tutor_id,student_id, status) VALUES ('$tutor_student_id' ,'$tutor_id' , '$student_id' , 'declined')" ;



if($conn->query($mysql_qry1) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry1."<br>".$conn->error;
}

//close the connection
$conn->close();

?>