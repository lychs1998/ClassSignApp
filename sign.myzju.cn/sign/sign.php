<?php
	error_reporting(E_ALL^E_WARNING);
    include('../config.php');
    if(isset($_POST['detailid'])&&isset($_POST['sno'])){
        if($_POST['detailid']!=''&&$_POST['sno']!=''){
             $detailid=$_POST['detailid'];
          	 $sno=$_POST['sno'];
          	 $time=time();
          	 $re=mysqli_query($con,"SELECT * FROM sign  WHERE detailid =$detailid AND endtime IS NULL ");
        if(mysqli_num_rows($re)>0){
          	 $row = mysqli_fetch_assoc($re);
          	 $signid=intval($row['signid']);
             $re=mysqli_query($con,"SELECT * FROM ss  WHERE signid =$signid AND sno like '$sno'");
          if(mysqli_num_rows($re)>0){
          	    $data2=array(
					'code'=>'1',
					'msg'=>'ok'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          }else{
             $re=mysqli_query($con,"insert into ss(sno,signtime,signid) values('$sno',$time,$signid);");
          if($re){
               $data2=array(
					'code'=>'1',
					'msg'=>'ok'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          }
          else{
               $data2=array(
					'code'=>'-1',
					'msg'=>'error'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          }
          }
         }
          else{
               $data2=array(
					'code'=>'0',
					'msg'=>'不存在课程'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
          } 
        }  else {
                $data2=array(
					'code'=>'-2',
					'msg'=>'非法请求'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
        }
    }
?>