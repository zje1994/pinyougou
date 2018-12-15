app.controller("baseController", function ($scope) {
//在javascript部分加入分页相关配置
    //分页配置
    $scope.paginationConf = {
        currentPage: 1,  				//当前页
        totalItems: 10,					//总记录数
        itemsPerPage: 10,				//每页记录数
        perPageOptions: [10, 20, 30, 40, 50], //分页选项，下拉选择一页多少条记录
        onChange: function () {			//页面变更后触发的方法
            $scope.reloadList();		//启动就会调用分页组件
        }
    };
    //分页组件
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }



    //定义记录选中的id数组
    $scope.selectIds=[];

    //跟新复选框选中的状态
    $scope.updateSelection=function ($event,id) {
        //判断选中状态
        if($event.target.checked){//选中状态
            $scope.selectIds.push(id);
        }else{
            //取消勾选，移除当前id值  //参数一：移除位置的元素的索引值  参数二：从该位置移除几个元素
            var index =$scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index,1);
        }
    }

    //解析json数据

    $scope.jsonStringParse=function (key,json) {
        var value="";
        var jsonArr =JSON.parse(json);
        for(var i = 0;i < jsonArr.length;i++ ){
            if(i != 0){
                value+=","+jsonArr[i][key];
            }else {
                value += jsonArr[i][key];
            }
        }
        return value;
    }


    //基于数组中对象的属性值 获取该对象并返回
    //[{"attributeName":"网络","attributeValue":["移动3G","移动4G"]}]
    $scope.getObjectByKey=function (list,key,value) {
        for(var i=0;i<list.length;i++){
            //存在对象时
            if(list[i][key]==value){
                return list[i];
            }
        }
        return null;
    }


})