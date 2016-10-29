<?php



$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

$tutor_email = $_POST["tutor_email"];

/* Select queries return a resultset */
$mysql_qry = mysqli_query($conn," SELECT COUNT(tutor_email) FROM TUTOR_TBL WHERE tutor_email = '$tutor_email' ") ;

$rows = array();
while($r = mysqli_fetch_assoc($mysql_qry) ) 
{
    $rows[] = $r;
}



print json_encode($rows);

//close the connection
$conn->close();
?>




