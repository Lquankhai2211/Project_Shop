<?php
include "connect.php";

header('Content-Type: application/json');
error_reporting(0);
ini_set('display_errors', 0);

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $email = trim($_POST['email']);
    $pass = trim($_POST['pass']);

    error_log("Email: " . $email);
    error_log("Password: " . $pass);


    $query = "SELECT * FROM `user` WHERE `email` = ?";
    $stmt = mysqli_prepare($conn, $query);

    if ($stmt) {
        mysqli_stmt_bind_param($stmt, "s", $email);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);

        if ($row = mysqli_fetch_assoc($result)) {
            if (password_verify($pass, $row['password'])) {
                $arr = [
                    'success' => true,
                    'message' => "Đăng nhập thành công",
                    'result' => [
                        'id' => $row['id'],
                        'email' => $row['email'],
                        'password' => $row['password'],
                        'username' => $row['username'],
                        'phone' => $row['phone']
                    ]
                ];
            } else {
                $arr = [
                    'success' => false,
                    'message' => "Mật khẩu không chính xác!"
                ];
            }
        } else {
            $arr = [
                'success' => false,
                'message' => "Email không tồn tại!"
            ];
        }
    } else {
        error_log("Lỗi truy vấn: " . mysqli_error($conn));
        $arr = [
            'success' => false,
            'message' => "Lỗi truy vấn"
        ];
    }

    // Trả về JSON đúng định dạng
    ob_clean();
    echo json_encode($arr, JSON_UNESCAPED_UNICODE);
    exit;
}
?>
