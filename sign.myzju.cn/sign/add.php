<?php
	error_reporting(E_ALL^E_WARNING);
    include('../config.php');
    if(isset($_POST['signid'])&&isset($_POST['sno'])&&$_POST['signid']!=''&&$_POST['sno']!=''){
             $signid=$_POST['signid'];
          	 $sno=$_POST['sno'];
          	 $time=time();
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
         
        }  else {
                $data2=array(
					'code'=>'-2',
					'msg'=>'非法请求'
				);
				$data_json = json_encode($data2,JSON_UNESCAPED_UNICODE);
				header('Content-type:text/json');
				echo $data_json;
        }
    
?>