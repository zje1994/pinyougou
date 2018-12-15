//自定义控制层
app.controller("specificationController",function ($scope,$controller,specificationService) {

    //控制器继承代码
    //参数一,继承的父控制器  参数二共享$scope对象
    $controller("baseController",{$scope:$scope})




    $scope.findAll=function () {
        specificationService.findAll().success(
            function (response) {
                $scope.list=response;
            })
    }

    $scope.searchEntity={};

    //分页条件查询请求
    $scope.search=function (pageNum,pageSize) {
        specificationService.search($scope.searchEntity,pageNum,pageSize).success(function (response) {
            $scope.paginationConf.totalItems=response.total;
            $scope.list=response.rows;
        })
    }



    //分页查询方法
    $scope.findPage=function (pageNum,pageSize) {
        specificationService.findPage(pageNum,pageSize).success(
            function(response){
                $scope.list = response.rows;
                $scope.paginationConf.totalItems=response.total;
            }
        );
    }

    $scope.findOne=function (id) {
        specificationService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }

    $scope.save=function () {
        var method=null;
        if($scope.entity.specification.id != null){
            method = specificationService.update($scope.entity);
        }else {
            method=specificationService.add($scope.entity);
        }
        method.success(
            function (response) {
                if (response.success){
                    $scope.reloadList();
                }else {
                    alert(response.message);
                }
            })

    }







    $scope.delete=function () {
        if(confirm("你确认要删除吗?")){
            specificationService.delete($scope.selectIds).success(
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



    //初始化entity对象
    $scope.entity={
        specificationOptions:[]
    }


    //添加规格行数
    $scope.addRow=function () {
        $scope.entity.specificationOptions.push({});

    }




    //删除规格行数
    $scope.delRow=function (index) {
        $scope.entity.specificationOptions.splice(index,1);

    }





});