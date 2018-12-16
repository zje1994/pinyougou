app.controller("indexController",function ($scope,$controller,contentService) {
    //继承
    $controller("baseController",{$scope:$scope});

    //根据分类id查询广告
    $scope.findByCategoryId=function (categoryId) {
        contentService.findByCategoryId(categoryId).success(function (reponse) {
            $scope.contentList=reponse;
        })
    }

})