//自定义服务层
app.service("specificationService",function ($http) {
    //查询所有
    this.findAll= function () {
        return $http.get("../specification/findAll.do");
    }

    //根据id查询一个
    this.findOne=function (id) {
        return $http.get("../specification/findOne.do?id="+id);
    }

    //分页方法
    this.findPage=function (pageNum,pageSize) {
        return $http.get('../specification/findPage.do?pageNum='+pageNum
            +"&pageSize="+pageSize)
    }

    //查询分页方法
    this.search=function (searchEntity,pageNum,pageSize) {
        return $http.post("../specification/search.do?pageNum="+pageNum+"&pageSize="+pageSize,searchEntity);
    }



    //更新方法
    this.update= function (entity) {
        return $http.post("../specification/update.do",entity);
    }

    //添加方法
    this.add= function (entity) {
        return $http.post("../specification/add.do",entity);
    }

    //删除方法
    this.delete=function (ids) {
        return $http.get("../specification/delete.do?ids="+ids);
    }


    //查询模板关联的品牌下拉列表数据
    this.selectSpecList=function () {
        return $http.get("../specification/selectSpecList.do");
    }

})