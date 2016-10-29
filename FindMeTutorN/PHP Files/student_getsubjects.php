<?php
//Required for login
//require "conn.php";
//Variable Names...

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";

$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$id = $_POST["StudentID"];



$mysql_qry = mysqli_query($conn,"SELECT subject_name, subject_course_code FROM SUBJECT_TBL,STUDENT_SUBJECT_TBL  WHERE STUDENT_SUBJECT_TBL.student_id ='$id' AND SUBJECT_TBL.subject_id = STUDENT_SUBJECT_TBL.subject_id");

$rows = array();
while($r = mysqli_fetch_assoc($mysql_qry) ) 
{
    $rows[] = $r;
}



print json_encode($rows);

//close the connection
$conn->close();
?>



