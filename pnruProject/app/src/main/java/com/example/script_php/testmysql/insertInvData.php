<?php
header("content-type:text/javascript;charset=utf-8");
include("config.php");

$ed_id = $_POST["ed_id"];
$ed_name = $_POST["ed_name"];
$ed_qu = $_POST["ed_qu"];
$ed_price = $_POST["ed_price"];

$sql = "INSERT INTO inventory (id, name, quantity, price)
VALUES ('$ed_id', '$ed_name', '$ed_qu', '$ed_price')";

if (mysqli_query($conn, $sql)) {

  $sql = "SELECT * FROM inventory";
  $result = mysqli_query($conn,$sql);

  while($row = mysqli_fetch_array($result)) {
    $tb_inv = array();
    $tb_inv["inv_id"] = $row["id"];
    $tb_inv["inv_name"] = $row["name"];
    $tb_inv["inv_quan"] = $row["quantity"];
    $tb_inv["inv_price"] = $row["price"];
    $reponse["tb_inv"]=array();
    array_push($reponse,$tb_inv);
    }
    echo json_encode($reponse);

} else {
  echo "Error: " . $sql . "<br>" . mysqli_error($conn);

}

mysqli_close($conn);
?>