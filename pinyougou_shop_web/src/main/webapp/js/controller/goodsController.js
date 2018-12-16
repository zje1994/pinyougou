 //控制层 
app.controller('goodsController' ,function($scope,$controller   ,goodsService,itemCatService,typeTemplateService,uploadService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){
		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			//设置商家介绍字段  通过kindEditor获取html内容
            $scope.entity.goodsDesc.introduction=editor.html();

			serviceObject=goodsService.add($scope.entity);//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//清空录入的商品数据
                    $scope.entity={};
                    editor.html("");//清除编辑器内容
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//查询一级分类
	$scope.selectItemCat1List=function () {
		itemCatService.findByParentId(0).success(function (response) {
			$scope.itemCat1List=response;
        })
    }

    //基于一级分类，查询关联的二级分类列表数据  参数一：监控变量值  参数二：监控从内容变化后，需要做的事情
	//newValue监控的变量变化后的值   oldValue监控的变量变化前的值
	$scope.$watch("entity.goods.category1Id",function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat2List=response;
        })
    });

    //基于二级分类，查询关联的三级分类列表数据  参数一：监控变量值  参数二：监控从内容变化后，需要做的事情
    //newValue监控的变量变化后的值   oldValue监控的变量变化前的值
    $scope.$watch("entity.goods.category2Id",function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat3List=response;
        })
    });

    //基于三级分类，查询关联的模板数据  参数一：监控变量值  参数二：监控从内容变化后，需要做的事情
    //newValue监控的变量变化后的值   oldValue监控的变量变化前的值
    $scope.$watch("entity.goods.category3Id",function (newValue,oldValue) {
        itemCatService.findOne(newValue).success(function (response) {
            $scope.entity.goods.typeTemplateId=response.typeId;
        })
    });

    //基于模板变化，查询关联的数据  参数一：监控变量值  参数二：监控从内容变化后，需要做的事情
    //newValue监控的变量变化后的值   oldValue监控的变量变化前的值
    $scope.$watch("entity.goods.typeTemplateId",function (newValue,oldValue) {
        typeTemplateService.findOne(newValue).success(function (response) {
           //将返回的品牌json字符串转为json数组对象
			$scope.brandList =JSON.parse(response.brandIds);
			//将返回的扩展属性json字符串转为json数组对象
			$scope.entity.goodsDesc.customAttributeItems = JSON.parse(response.customAttributeItems);

        });
        //查询规格列表数据
        typeTemplateService.findSpecList(newValue).success(function (response) {
            $scope.specList =response;
        })
    });




    //图片上传方法
	$scope.uploadFile=function () {
		uploadService.uploadFile().success(function (response) {
			if(response.success){
				//上传成功
				$scope.image_entity.url=response.message;


			}else {
				//上传失败
				alert(response.message)
			}
        })
    }

    //初始化entity对象
	$scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]},itemList:[]};

	//添加商品图片以及商品描述信息
	$scope.addImage_entity=function () {
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }
	
    //移除商品图片以及信息
	$scope.delImage_entity=function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    }

    //组装商品勾选时规格属性变化
	$scope.updateSpecAttribute=function ($event, specName, specOption) {
		//判断规格名称是否在勾选的规格列表中

		var specObject=$scope.getObjectByKey($scope.entity.goodsDesc.specificationItems,"attributeName",specName)

		if(specObject!=null){
			//如果存在
			//判断勾选规格选项还是取消规格选项
			if($event.target.checked){
				//在原有的规格数组中添加
				specObject.attributeValue.push(specOption);
			}else {
                //在原有的规格数组中移除
				var index = specObject.attributeValue.indexOf(specOption);
                //如果取消勾选规格选项中的所有选项,从规格列表中删除规格数据源
                specObject.attributeValue.splice(index,1);
				if(specObject.attributeValue.length<=0){
                    var index1= $scope.entity.goodsDesc.specificationItems.indexOf(specObject);
                    $scope.entity.goodsDesc.specificationItems.splice(index1,1);
				}
			}
		}else {
			//如果不存在
			$scope.entity.goodsDesc.specificationItems.push({"attributeName":specName,"attributeValue":[specOption]});
		}


        /*entity.itemList=[{"spec":{"网络":"移动3G","机身内存":"16G"},"price":0,"num":99999,"status":1,"isDefault":0},
            {"spec":{"网络":"移动4G","机身内存":"16G"},"price":0,"num":99999,"status":1,"isDefault":0}]*/

        //specList:[{"attributeName":"网络","attributeValue":["移动3G"]},{"attributeName":"机身内存","attributeValue":["16G"]}]
		//创建itemList数组
		$scope.createItemList=function () {
			//初始化itemList
			$scope.entity.itemList=[{spec:{},price:0,num:99999,status:"1",isDefault:"0"}];
			//勾选规格的结果集
			var specList =$scope.entity.goodsDesc.specificationItems;
			//判断勾选的结果集是否为空
			if (specList.length ==0){
				$scope.entity.itemList=[];
			}
            for(var i=0;i<specList.length;i++){
                //动态为列表中对象的spec属性赋值方法，动态生成sku列表 行列方法
                $scope.entity.itemList=addColumn($scope.entity.itemList,specList[i].attributeName,specList[i].attributeValue);
            }

        }
		addColumn=function (itemList,specName,specOptions) {
			//定义一个新的数组
			var newList=[];
			//{"机身内存":"16G","网络":"联通3G"}
			for (var i=0;itemList.length > i;i++){
                //{spec:{},price:0,num:99999,status:1,isDefault:0}
				var item=itemList[i];
				//遍历勾选的规格数组
				for(var j=0;specOptions.length>j;j++){
					var newItem = JSON.parse(JSON.stringify(item))
                    newItem.spec[specName]=specOptions[j];

                    newList.push(newItem);
				}
			}
			return newList;
        }
        //定义商品状态数组
        $scope.statusArr=['未审核','已审核','审核未通过','关闭'];

        //初始化itemCatList集合
		$scope.itemCatArray=[];

        //定义查询所有分类的方法
        $scope.selectItemCatList=function () {
            itemCatService.findAll().success(function (response) {
                //["","手机","电脑" ...]
                //定义记录分类列表的数组
                for(var i=0;i<response.length;i++){
                    //response[i];分类对象
                    $scope.itemCatList[response[i].id]=response[i].name;

                }

            })
        }





    }















    
});	
