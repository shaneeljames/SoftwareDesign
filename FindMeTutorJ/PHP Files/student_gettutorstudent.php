<?php
//Required for login
//require "conn.php";
//Variable Names...

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";

$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$id = $_POST["TutorStudentID"];



$mysql_qry = mysqli_query($conn,"SELECT description,student_checkin, student_checkout, tutor_checkin, tutor_checkout, paid  FROM TUTOR_STUDENT_TBL WHERE tutor_student_id='$id' ");

$rows = array();
while($r = mysqli_fetch_assoc($mysql_qry) ) 
{
    $rows[] = $r;
}



print json_encode($rows);

//close the connection
$conn->close();
?>



