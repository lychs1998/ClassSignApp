# ClassSignApp
“ClassSignApp”是一个利用wifi实现验证位置的课堂签到应用。教师端抽取一个wifi热点的BSSID传到服务器，学生端获得周围wifi列表校验是否存在相同BSSID的wifi，如果有则签到成功。教师端在结束签到后获得签到结果，可以通过直接点名确认是否是系统错误，并在最后可以通过点击修改签到状态。在此基础上，我们还做了自定义的课程表界面与当天课程界面。

## 注意
源码需要修改后才能使用，app端不支持注册功能，推荐自行编写web后端。

WifiThread为签到线程，StartThread为开始签到线程（推荐在生产环境下使用消息队列来实现签到状态的订阅）

## 演示视屏

## 后端
