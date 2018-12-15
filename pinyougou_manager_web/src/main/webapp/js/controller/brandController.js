//自定义控制层
app.controller("brandController",function ($scope,$controller,brandService) {

    //控制器继承代码
    //参数一,继承的父控制器  参数二共享$scope对象
    $controller("baseController",{$scope:$scope})




    $scope.findAll=function () {
        brandService.findAll().success(
            function (response) {
                $scope.list=response;
            })
    }


    //分页查询方法
    $scope.search=function (pageNum,pageSize) {
        brandService.findPage(pageNum,pageSize).success(
            function(response){
                $scope.list = response.rows;
                $scope.paginationConf.totalItems=response.total;
            }
        );
    }

    $scope.findOne=function (id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }

    $scope.save=function () {
        if($scope.entity.id != null){
            brandService.update($scope.entity).success(
                function (response) {
                    if (response.success){
                        $scope.reloadList();
                    }else {
                        alert(response.message);
                    }
                }
            )
        }else {
            brandService.add($scope.entity).success(
                function (response) {
                    if (response.success){
                        $scope.reloadList();
                    }else {
                        alert(response.message);
                    }
                }
            )
        }
    }




    $scope.delete=function () {
        if(confirm("你确认要删除吗?")){
            brandService.delete($scope.selectIds).success(
                function (response) {
                    if(response.success){
                        $scope.reloadList();
                    }else {
                        alert(response.message)
                    }
                }
            )
        }
    }
});