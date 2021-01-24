<?php
header("content-type:text/javascript;charset=utf-8");
include("config.php");

$loc_la = $_POST["loc_la"];
$loc_long = $_POST["loc_long"];
$loc_date = date('Y-m-d H:i:s');

$sql = "INSERT INTO location (loc_la, log_long, loc_date)
VALUES ('$loc_la', '$loc_long', '$loc_date')";

if (mysqli_query($conn, $sql)) {

  $sql = "SELECT * FROM location order by loc_date desc";
  $result = mysqli_query($conn,$sql);

  while($row = mysqli_fetch_array($result)) {
    $tb_loc = array();
    $tb_loc["loc_la"] = $row["loc_la"];
    $tb_loc["loc_long"] = $row["loc_long"];
    $tb_loc["loc_date"] = $row["loc_date"];
    $reponse["tb_inv"]=array();
    array_push($reponse,$tb_loc);
    }
    echo json_encode($reponse);

} else {
  echo "Error: " . $sql . "<br>" . mysqli_error($conn);

}

mysqli_close($conn);
?>