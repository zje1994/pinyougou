app.controller("indexController",function ($scope,$controller,contentService) {
    //继承
    $controller("baseController",{$scope:$scope});

    //根据分类id查询广告
    $scope.findByCategoryId=function (categoryId) {
        contentService.findByCategoryId(categoryId).success(function (reponse) {
            $scope.contentList=reponse;
        })
    }

    $scope.keywords="";

    $scope.search=function () {
        location.href="http://search.pinyougou.com/search.html#?keywords="+$scope.keywords;
    }

})