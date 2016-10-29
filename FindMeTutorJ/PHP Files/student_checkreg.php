<?php



$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$student_email = $_POST["student_email"];

/* Select queries return a resultset */
$mysql_qry = mysqli_query($conn," SELECT COUNT(student_email) FROM STUDENT_TBL WHERE student_email = '$student_email' ") ;

$rows = array();
while($r = mysqli_fetch_assoc($mysql_qry) ) 
{
    $rows[] = $r;
}



print json_encode($rows);

//close the connection
$conn->close();
?>




