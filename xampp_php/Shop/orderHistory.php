<?php
include 'connect.php';

$user_id = $_GET['user_id']; // Lấy user_id từ request GET

if (!$user_id) {
    echo json_encode(["status" => "error", "message" => "Thiếu user_id"]);
    exit;
}

$sql = "SELECT o.id AS order_id, o.user_id, o.address, o.amount AS order_amount, o.total_price, o.phone, o.status,
               od.product_id, od.amount AS product_amount, od.price 
        FROM orders o
        LEFT JOIN order_details od ON o.id = od.order_id
        WHERE o.user_id = ?";

$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

$orders = [];

while ($row = $result->fetch_assoc()) {
    $order_id = $row['order_id'];

    if (!isset($orders[$order_id])) {
        $orders[$order_id] = [
            'order_id' => $row['order_id'],
            'user_id' => $row['user_id'],
            'address' => $row['address'],
            'order_amount' => $row['order_amount'],
            'total_price' => $row['total_price'],
            'phone' => $row['phone'],
            'status' => $row['status'],
            'details' => []
        ];
    }

    // Thêm thông tin của order_detail vào order tương ứng
    $orders[$order_id]['details'][] = [
        'product_id' => $row['product_id'],
        'amount' => $row['product_amount'],
        'price' => $row['price']
    ];
}

$stmt->close();
$conn->close();

echo json_encode(array_values($orders));
