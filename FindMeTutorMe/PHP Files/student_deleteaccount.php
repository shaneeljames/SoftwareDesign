<?php

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);


$student_id = $_POST["student_id"];


//execute the SQL query and return records

 $mysql_qry =  "UPDATE STUDENT_TBL SET status= 'Deleted' WHERE STUDENT.student_id = '$student_id' " ;



if($conn->query($mysql_qry) === TRUE){
echo "Update Successful";
}else{
echo "Update Unsuccessful".$mysql_qry."<br>".$conn->error;
}

//close the connection
$conn->close();

?>