<?php
$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$tutor_student_id = $_POST["tutor_student_id"];




//execute the SQL query and return records
$mysql_qry = "SELECT tutor_checkin FROM `TUTOR_STUDENT_TBL` WHERE tutor_student_id = '$tutor_student_id'";


$rows = array();
while($r = mysqli_fetch_assoc($mysql_qry) ) 
{
    $rows[] = $r;
}



print json_encode($rows);

//close the connection
$conn->close();
?>


