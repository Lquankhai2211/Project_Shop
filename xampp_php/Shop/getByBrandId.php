<?php
include "connect.php";

$brandId = isset($_GET['brandId']) ? intval($_GET['brandId']) : 0;

if ($brandId > 0) {
    $stmt = $conn->prepare("SELECT * FROM product WHERE brand_id = ?");
    $stmt->bind_param("i", $brandId);
    $stmt->execute();
    $result = $stmt->get_result();

    $data = array();
    while ($row = $result->fetch_assoc()) {
        $data[] = $row;
    }

    if (!empty($data)) {
        $arr = [
            'success' => true,
            'message' => "Success",
            'result' => $data
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "No products found"
        ];
    }

    $stmt->close();
} else {
    $arr = [
        'success' => false,
        'message' => "Invalid brandId"
    ];
}

print_r(json_encode($arr));
?>
