<?php
$host = "localhost";
$user = "root";
$pass = "";
$database = "appbanhang_he170154";

$conn = mysqli_connect($host, $user, $pass, $database);
mysqli_set_charset($conn, "utf8");

if ($conn) {
    // code...
}
?>
