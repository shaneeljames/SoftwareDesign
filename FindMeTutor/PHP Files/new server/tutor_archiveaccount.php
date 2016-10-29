<?php

$db_name = "a6782245_FindMeT";
$mysql_username = "a6782245_jadon";
$mysql_password = "admin1234";
$server_name = "mysql11.000webhost.com";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);


$tutor_id = $_POST["tutor_id"];

//execute the SQL query and return records


 $mysql_qry1 = "UPDATE TUTOR_TBL SET tutor_status = 'Deleted' WHERE tutor_id = '$tutor_id'" ;

 




if($conn->query($mysql_qry1) === TRUE){
echo "Insert Successful";
}else{
echo "Archive to tutor Unsuccessful".$mysql_qry1."<br>".$conn->error;
}



//close the connection
$conn->close();

?>