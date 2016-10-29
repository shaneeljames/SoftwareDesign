<?php

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$student_id = $_POST["student_id"];


//execute the SQL query and return records


 $mysql_qry1 =  mysqli_query($conn,"UPDATE STUDENT_TBL SET STUDENT_TBL.student_rating = (SELECT SUM(student_rating)/COUNT(student_rating) FROM TUTOR_STUDENT_TBL  WHERE student_id = '$student_id' AND status = 'Complete' ) WHERE student_id = '$student_id" );

if($conn->query($mysql_qry1) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry1."<br>".$conn->error;
}

//close the connection
$conn->close();

?>