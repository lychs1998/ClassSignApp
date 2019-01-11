<?php
	error_reporting(E_ALL^E_WARNING);
    include('../config.php');
    if(isset($_POST['user']) && isset($_POST['pwd'])&& isset($_POST['flag'])&&isset($_POST['newpwd'])&&$_POST['newpwd']!=''){
             $user=$_POST['user'];
      		 $flag=$_POST['flag'];
      		 $newpwd=md5($_POST['newpwd']);
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
                $re=mysqli_query($con,"update student set pwd='$newpwd' where sno like '$user'");
               }else if($flag==2){
               $re=mysqli_query($con,"update teacher set pwd='$newpwd' where tno like '$user'");
               }
               if($re){
               	$data2=array(
					'id'=>1,
					'msg'=>'ok'
				);}
               else{
               		$data2=array(
					'id'=>-3,
					'msg'=>'修改失败'
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