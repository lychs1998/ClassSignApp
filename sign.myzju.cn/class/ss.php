<?php
include('../config.php');

if(isset($_POST['courseid'])){
$courseid=$_POST['courseid'];
$re=mysqli_query($con,"SELECT * FROM cs left outer join student on cs.sno like student.sno WHERE cs.courseid like '$courseid'");
if(mysqli_num_rows($re)==0){
mysqli_close($con);
$data2=array(
'id'=>'-1',
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
mysqli_close($con);
$data_json = json_encode($data,JSON_UNESCAPED_UNICODE);
header('Content-type:text/json');
echo $data_json;
}}
else
{mysqli_close($con);
$data2=array(
'id'=>'0',
'request'=>'fail',
'msg'=>'无效的请求'
);
$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
header('Content-type:text/json');
echo $data_json;
}
?>