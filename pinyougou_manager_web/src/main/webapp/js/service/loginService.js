app.service("loginService",function ($http) {

        //获取用户名
        this.getloginName=function () {
            return $http.get("../login/getUserMessage.do");
    }















})