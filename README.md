# ClassSignApp
“ClassSignApp”是一个利用wifi实现验证位置的课堂签到应用。教师端抽取一个wifi热点的BSSID传到服务器，学生端获得周围wifi列表校验是否存在相同BSSID的wifi，如果有则签到成功。教师端在结束签到后获得签到结果，可以通过直接点名确认是否是系统错误，并在最后可以通过点击修改签到状态。在此基础上，我们还做了自定义的课程表界面与当天课程界面。

## 注意
源码需要修改后才能使用，app端不支持注册功能，推荐自行编写web后端，但项目中也提供了测试后端的源码。

WifiThread为签到线程，StartThread为开始签到线程（推荐在生产环境下使用消息队列来实现签到状态的订阅）

## 演示视屏
[视屏地址](http://yun.clovef.top/?/Final.mp4)

## 后端
sign.myzju.cn文件夹下为web服务器后端，使用php+mysql搭建。

install.sql为服务器数据库创建与插入默认数据的SQL脚本。

使用时需要修改android源码中的请求接口与后端config.php的数据库名称与密码。
