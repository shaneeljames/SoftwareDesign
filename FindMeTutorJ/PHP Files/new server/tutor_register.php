

<?php
require "conn.php";
$surname = $_POST["Surname"];
$contactnumber = $_POST["Contactnumber"];
$StudentNumber = $_POST["StudentNumber"];
$email = $_POST["Email"];
$password = $_POST["Password"];


if($name==""){
echo "Incorrect Insert";
$conn->close();
}else{
//execute the SQL query and return records
$mysql_qry = "Insert into TUTOR_TBL (tutor_fname,tutor_lname,tutor_contact_num,tutor_student_num, tutor_email,tutor_password) Values('$name','$surname','$contactnumber','$StudentNumber','email','$password')";


if($conn->query($mysql_qry) === TRUE){
echo "Insert Successful";
}else{
echo "Insert Unsuccessful".$mysql_qry."<br>".$conn->error;
}

//close the connection
$conn->close();
}
?>