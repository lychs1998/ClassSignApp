<?php
	error_reporting(E_ALL^E_WARNING);
    include('../config.php');
    if(isset($_POST['bssid'])&&isset($_POST['detailid'])){
        if($_POST['bssid']!=''&&$_POST['detailid']!=''){
             $bssid=$_POST['bssid'];
          	 $detailid=$_POST['detailid'];
          	 $time=time();
          	 $re=mysqli_query($con,"select * from class where detailid=$detailid" );
        if(mysqli_num_rows($re)>0){
             $re=null;
             $re=mysqli_query($con,"insert into sign(detailid,addtime,bssid) values($detailid,$time,'$bssid');");
          if($re){
               $data2=array(
					'code'=>1,
					'msg'=>'ok'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          }
          else{ 
               $data2=array(
					'code'=>-1,
					'msg'=>'error'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          }
         }
          else{
               $data2=array(
					'code'=>0,
					'msg'=>'不存在课程'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          } 
        }  else {
                $data2=array(
					'code'=>-2,
					'msg'=>'非法请求'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
        }
    }
?>