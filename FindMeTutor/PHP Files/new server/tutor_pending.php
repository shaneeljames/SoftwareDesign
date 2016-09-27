b<?php



$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$tutor_id = $_POST["tutor_id"];

/* Select queries return a resultset */
$mysql_qry = mysqli_query($conn,"  SELECT  TUTOR_STUDENT_TBL.tutor_student_id, TUTOR_STUDENT_TBL.student_id, TUTOR_STUDENT_TBL.date,TUTOR_STUDENT_TBL.time , description, SUBJECT_TBL.subject_name,SUBJECT_TBL.subject_course_code , student_fname , student_lname FROM TUTOR_STUDENT_TBL, TUTOR_SUBJECT_TBL,SUBJECT_TBL, STUDENT_TBL ,TUTOR_CONFIRMED_TBL WHERE TUTOR_SUBJECT_TBL.tutor_id = '$tutor_id' AND TUTOR_STUDENT_TBL.subject_id =  TUTOR_SUBJECT_TBL.subject_id AND TUTOR_STUDENT_TBL.subject_id = SUBJECT_TBL.subject_id AND TUTOR_STUDENT_TBL.student_id = STUDENT_TBL.student_id AND TUTOR_STUDENT_TBL.status = 'pending' AND TUTOR_CONFIRMED_TBL.tutor_student_id = TUTOR_STUDENT_TBL.tutor_student_id AND TUTOR_CONFIRMED_TBL.tutor_id = '$tutor_id' ") ;

$rows = array();
while($r = mysqli_fetch_assoc($mysql_qry) ) 
{
    $rows[] = $r;
}



print json_encode($rows);

//close the connection
$conn->close();
?>




