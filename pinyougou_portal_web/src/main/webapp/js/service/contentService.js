//服务层
app.service('contentService',function($http){


    //根据广告类型查询广告
	this.findByCategoryId=function (categoryId) {
		return $http.get('content/findByCategoryId.do?categoryId='+categoryId);
    }
});
