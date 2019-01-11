<?php
include('../config.php');

if(isset($_POST['detailid'])){
$detailid=$_POST['detailid'];
$re=mysqli_query($con,"SELECT * FROM sign where detailid=$detailid and endtime is null");
if(mysqli_num_rows($re)==0){
mysqli_close($con);
$data2=array(
'id'=>'-1',
'request'=>'fail'
);
$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
header('Content-type:text/json');
echo $data_json;
}else{
$time=time();
$row = mysqli_fetch_assoc($re);
$signid=$row['signid'];
$rf=mysqli_query($con,"update sign set endtime=$time where signid=$signid");
if($rf){
  $re=mysqli_query($con,"SELECT * FROM ss left OUTER JOIN student on ss.sno like student.sno WHERE ss.signid=$signid");
  while($row = mysqli_fetch_assoc($re)){
		$data[]=$row;
  }
  mysqli_close($con);
  $data_json = json_encode($data,JSON_UNESCAPED_UNICODE);
  header('Content-type:text/json');
  echo $data_json;
}else{
$data2=array(
'id'=>'-2',
'request'=>'再次尝试'
);
$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
header('Content-type:text/json');
echo $data_json;
}
}
}else{
$data2=array(
'id'=>'-3',
'request'=>'error'
);
$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
header('Content-type:text/json');
echo $data_json;
}
?>