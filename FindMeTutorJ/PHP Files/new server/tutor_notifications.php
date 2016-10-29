<?php



$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$tutor_id = $_POST["tutor_id"];
$email = $_POST["Email"];
$password = $_POST["Password"];
/* Select queries return a resultset */
$mysql_qry = mysqli_query($conn,"SELECT SUBJECT_TBL.subject_name,SUBJECT_TBL.subject_course_code,       STUDENT_TBL.student_fname,STUDENT_TBL.student_lname,  TUTOR_STUDENT_TBL.date,TUTOR_STUDENT_TBL.time ,TUTOR_STUDENT_TBL.description FROM TUTOR_STUDENT_TBL,TUTOR_SUBJECT_TBL,SUBJECT_TBL,STUDENT_TBL WHERE TUTOR_SUBJECT_TBL.tutor_id= '$tutor_id' AND TUTOR_SUBJECT_TBL.subject_id = TUTOR_STUDENT_TBL.subject_id and SUBJECT_TBL.subject_id =  TUTOR_STUDENT_TBL.subject_id and STUDENT_TBL.student_id = TUTOR_STUDENT_TBL.student_id and TUTOR_STUDENT_TBL.description = 'pending'") ;

$rows = array();
while($r = mysqli_fetch_assoc($mysql_qry) ) 
{
    $rows[] = $r;
}



print json_encode($rows);

//close the connection
$conn->close();
?>




