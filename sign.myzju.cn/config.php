<?php
$host = 'localhost';
$database = '数据库名称';
$username = '数据库用户';
$password = '用户密码';

$con=mysqli_connect($host,$database,$password,$database); 
if (mysqli_connect_errno($con)) 
{ 
    echo "连接 MySQL 失败: " . mysqli_connect_error(); 
}
?>
