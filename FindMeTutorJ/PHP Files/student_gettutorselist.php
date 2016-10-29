<?php
//Required for login
//require "conn.php";
//Variable Names...

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";

$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$id = $_POST["SubjectID"];



$mysql_qry = mysqli_query($conn,"SELECT  TUTOR_TBL.tutor_id, TUTOR_TBL.tutor_student_num, TUTOR_TBL.tutor_fname, TUTOR_TBL.tutor_lname,  TUTOR_TBL.tutor_email FROM  TUTOR_TBL, TUTOR_SUBJECT_TBL WHERE TUTOR_TBL_tutor_id = TUTOR_SUBJECT_TBL.tutor_id AND TUTOR_SUBJECT_TBL.subject_id = '$id' ");

$rows = array();
while($r = mysqli_fetch_assoc($mysql_qry) ) 
{
    $rows[] = $r;
}



print json_encode($rows);

//close the connection
$conn->close();
?>



