UPDATE table_name
SET column1 = value1, column2 = value2...., columnN = valueN
WHERE [condition];



<?php

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);


$tutor_id = $_POST["tutor_id"];
$tutor_fname = $_POST["tutor_fname"];
$tutor_lname = $_POST["tutor_lname"];
$tutor_contact_num = $_POST["tutor_contact_num"];
$tutor_student_num = $_POST["tutor_student_num"];
$tutor_qualifications = $_POST["tutor_qualifications"];
$tutor_email = $_POST["tutor_email"];
$tutor_password= $_POST["tutor_password"];


//execute the SQL query and return records

 $mysql_qry = "UPDATE TUTOR_TBL SET tutor_fname = '$tutor_fname' , tutor_lname = '$tutor_lname' , tutor_contact_num = '$tutor_contact_num' , tutor_student_num = '$tutor_student_num' , tutor_qualifications = '$tutor_qualifications' ,  tutor_email = '$tutor_email' , tutor_password=  '$tutor_password'  WHERE tutor_id = '$tutor_id'" ;


if($conn->query($mysql_qry) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry."<br>".$conn->error;
}

//close the connection
$conn->close();

?>?>