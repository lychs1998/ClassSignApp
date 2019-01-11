<?php
	error_reporting(E_ALL^E_WARNING);
    include('../config.php');
    if(isset($_POST['user'])&&$_POST['user']!=''){
        if(isset($_POST['user']) && isset($_POST['pwd'])&&$_POST['pwd']!=''&&isset($_POST['username'])&&$_POST['username']!=''&&isset($_POST['flag'])){
             $user=$_POST['user'];
          	 $flag=$_POST['flag'];
             $pwd=md5($_POST['pwd']);
             $username=$_POST['username'];
          if($flag==1){
             $re=mysqli_query($con,"select * from student where sno like '$user'");
          }else if($flag==2){
          	 $re=mysqli_query($con,"select * from teacher where tno like '$user'");
          }else{
          	   $data2=array(
					'id'=>-1,
					'msg'=>'error'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
                exit;
          }
        if(mysqli_num_rows($re)==0){
             $re=null;
          if($flag==1){
             $re=mysqli_query($con,"insert into student(sno,pwd,sname) values('$user','$pwd','$username');");
          }else if($flag==2){
          	 $re=mysqli_query($con,"insert into teacher(tno,pwd,tname) values('$user','$pwd','$username');");
          }
          if($re){
               $data2=array(
					'id'=>1,
					'msg'=>'ok'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          }
          else{ 
               $data2=array(
					'id'=>-1,
					'msg'=>'error'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          }
         }
          else{
               $data2=array(
					'id'=>0,
					'msg'=>'用户名已存在'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          } 
        }  else {
                $data2=array(
					'id'=>-2,
					'msg'=>'非法请求'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
        }
    }
?>