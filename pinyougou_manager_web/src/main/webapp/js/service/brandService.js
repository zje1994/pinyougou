//自定义服务层
app.service("brandService",function ($http) {
    //查询所有
    this.findAll= function () {
        return $http.get("../brand/findAll.do");
    }

    //根据id查询一个
    this.findOne=function (id) {
        return $http.get("../brand/findOne.do?id="+id);
    }

    //分页方法
    this.findPage=function (pageNum,pageSize) {
        return $http.get('../brand/findPage.do?pageNum='+pageNum
            +"&pageSize="+pageSize)
    }

    //更新方法
    this.update= function (entity) {
        return $http.post("../brand/update.do",entity);
    }

    //添加方法
    this.add= function (entity) {
        return $http.post("../brand/add.do",entity);
    }

    //删除方法
    this.delete=function (ids) {
        return $http.get("../brand/delete.do?ids="+ids);
    }

    //查询模板关联的品牌下拉列表数据
    this.selectBrandList=function () {
        return $http.get("../brand/selectBrandList.do");
    }

})