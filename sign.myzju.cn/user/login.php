<?php
	error_reporting(E_ALL^E_WARNING);
    include('../config.php');
    if(isset($_POST['user']) && isset($_POST['pwd'])&& isset($_POST['flag'])){
             $user=$_POST['user'];
      		 $flag=$_POST['flag'];
      		 if($flag==1){
                 $re=mysqli_query($con,"SELECT * FROM student where sno like '$user'");
             }else if($flag==2){
                 $re=mysqli_query($con,"SELECT * FROM teacher where tno like '$user'");
             }else{
                $data2=array(
					'id'=>-2,
					'msg'=>'error'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
               exit;
             }
             $row = mysqli_fetch_assoc($re);
             $pwd=$row['pwd'];
             if(md5($_POST['pwd'])==$pwd){
               if($flag==1){
                $data2=array(
					'id'=>1,
					'msg'=>$row['sname']
				);
               }else if($flag==2){
               	$data2=array(
					'id'=>1,
					'msg'=>$row['tname']
				);
               }
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
             }else{
             	$data2=array(
					'id'=>'-3',
					'msg'=>'密码错误'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
             }
    }else{
                     $data2=array(
					'id'=>'-2',
					'msg'=>'error'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
    }
?>