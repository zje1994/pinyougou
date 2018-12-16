app.controller("loginController",function ($scope,$controller,loginService) {


    $controller("baseController",{$scope:$scope});

    $scope.loginName=function () {
        loginService.getloginName().success(
            function (response) {
                $scope.loginName=response.loginName;
            }
        )
    }







})