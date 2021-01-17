<?php
header("content-type:text/javascript;charset=utf-8");
include("config.php");

$inv_id = $_POST["inv_id"];

// sql to delete a record
$sql = "DELETE FROM inventory WHERE id='$inv_id'";

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