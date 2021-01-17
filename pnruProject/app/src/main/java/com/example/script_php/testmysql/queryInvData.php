<?php
header("content-type:text/javascript;charset=utf-8");
include("config.php");


  $sql = "SELECT * FROM inventory";
  $result = mysqli_query($conn,$sql);

  if (mysqli_num_rows($result) > 0) {
    while($row = mysqli_fetch_array($result)) {
        $tb_inv = array();
        $tb_inv["inv_id"] = $row["id"];
        $tb_inv["inv_name"] = $row["name"];
        $tb_inv["inv_quan"] = $row["quantity"];
        $tb_inv["inv_price"] = $row["price"];
        $reponse["tb_inv"]=array();
        array_push($reponse,$tb_inv);
        }

    }else{
        $tb_inv = array();
        $tb_inv["inv_id"] = "";
        $tb_inv["inv_name"] = "";
        $tb_inv["inv_quan"] = "";
        $tb_inv["inv_price"] = "";
        $reponse["tb_inv"]=array();
        array_push($reponse,$tb_inv);
    }
echo json_encode($reponse);

mysqli_close($conn);
?>