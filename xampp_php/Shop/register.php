<?php
include "connect.php";

$email = mysqli_real_escape_string($conn, $_POST['email']);
$password = password_hash($_POST['password'], PASSWORD_BCRYPT); 
$username = mysqli_real_escape_string($conn, $_POST['username']);
$phone = mysqli_real_escape_string($conn, $_POST['phone']);

// Kiểm tra email đã tồn tại chưa
$query = "SELECT * FROM user WHERE email = ?";
$stmt = mysqli_prepare($conn, $query);
mysqli_stmt_bind_param($stmt, "s", $email);
mysqli_stmt_execute($stmt);
$result = mysqli_stmt_get_result($stmt);
$numrow = mysqli_num_rows($result);

if ($numrow > 0) {
    $arr = [
        'success' => false,
        'message' => "Email đã tồn tại"
    ];
} else {
    // Chèn dữ liệu mới vào bảng user
    $query = "INSERT INTO user (email, password, username, phone) VALUES (?, ?, ?, ?)";
    $stmt = mysqli_prepare($conn, $query);
    mysqli_stmt_bind_param($stmt, "ssss", $email, $password, $username, $phone);
    $execute = mysqli_stmt_execute($stmt);

    if ($execute) {
        $arr = [
            'success' => true,
            'message' => "Đăng ký thành công"
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "Đăng ký không thành công"
        ];
    }
}

echo json_encode($arr);
?>
