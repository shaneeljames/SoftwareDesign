<?php
$link = mysqli_connect("localhost", "root", "asdf", "findmetutor");
$email = $_POST["Email"];
$password = $_POST["Password"];
/* Select queries return a resultset */
if ($r = mysqli_query($link, "SELECT *  FROM TUTOR_TBL where tutor_email='$email' and tutor_password='$password'")) {

while ($row=$r->fetch_assoc()){
$output[]=$row;
}
}
mysqli_close($link);
echo json_encode($output);
?>
