<?php
error_reporting(E_ALL^E_WARNING);
include('../config.php');
if(isset($_POST['detailid'])){
$detailid=$_POST['detailid'];
$re=mysqli_query($con,"SELECT * FROM sign where detailid=$detailid and endtime is null");
if(mysqli_num_rows($re)==0){
mysqli_close($con);
$data2=array(
'signid'=>'-1',
'request'=>'fail',
'msg'=>'查询数据不存在'
);
$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
header('Content-type:text/json');
echo $data_json;
}else{
while($row = mysqli_fetch_assoc($re)){
		$data[]=$row;
}
$re=$data[0];
mysqli_close($con);
$data_json = json_encode($re,JSON_UNESCAPED_UNICODE);
header('Content-type:text/json');
echo $data_json;
}
}
else
{
mysqli_close($con);
$data2=array(
'signid'=>'0',
'request'=>'fail',
'msg'=>'无效的请求'
);
$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
header('Content-type:text/json');
echo $data_json;
}
?>