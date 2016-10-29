UPDATE table_name
SET column1 = value1, column2 = value2...., columnN = valueN
WHERE [condition];



<?php

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);


$student_id = $_POST["student_id"];
$student_fname = $_POST["student_fname"];
$student_lname = $_POST["student_lname"];
$student_contact_num = $_POST["student_contact_num"];
$student_password= $_POST["student_password"];


//execute the SQL query and return records

 $mysql_qry = "UPDATE STUDENT_TBL SET student_fname = '$tutor_fname' , student_lname = '$student_lname' ,student_contact_num = '$student_contact_num' , student_password=  '$student_password'  WHERE student_id = '$student_id'" ;


if($conn->query($mysql_qry) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry."<br>".$conn->error;
}

//close the connection
$conn->close();

?>?>