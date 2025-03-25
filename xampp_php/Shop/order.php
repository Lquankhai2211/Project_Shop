<?php
include "connect.php";

header('Content-Type: application/json');
error_reporting(0);
ini_set('display_errors', 0);

$user_id = $_POST['user_id'];
$address = $_POST['address'];
$phone = $_POST['phone'];
$total_price = $_POST['total_price'];
// Lấy dữ liệu từ POST
$order_details = json_decode($_POST['order_details'], true);
$total_amount = $_POST['amount'];
$status = "Đang xử lý";

$query = "INSERT INTO orders (`user_id`, `address`, `phone`, `total_price`, `amount`, `status`) 
          VALUES (?, ?, ?, ?, ?, ?)";
$stmt = $conn->prepare($query);
$stmt->bind_param("issdis", $user_id, $address, $phone, $total_price, $total_amount, $status);
$execute = $stmt->execute();

if ($execute) {
    $order_id = $stmt->insert_id;

    // Thêm vào order_details
    foreach ($order_details as $value) {
        $product_id = $value['product_id'];
        $amount = $value['amount'];
        $price = $value['product_price'];

        $query_detail = "INSERT INTO order_details (`order_id`, `product_id`, `amount`, `price`) 
                         VALUES (?, ?, ?, ?)";
        $stmt_detail = $conn->prepare($query_detail);
        $stmt_detail->bind_param("iiid", $order_id, $product_id, $amount, $price);
        $execute_detail = $stmt_detail->execute();

        if (!$execute_detail) {
            error_log("Lỗi khi thêm order_detail: " . $stmt_detail->error);
        }
    }

    echo json_encode([
        'success' => true,
        'message' => 'Đơn hàng đã được thêm thành công'
    ]);
} else {
    echo json_encode([
        'success' => false,
        'message' => 'Thêm đơn hàng thất bại'
    ]);
}

?>
