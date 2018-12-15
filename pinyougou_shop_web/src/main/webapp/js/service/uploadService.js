app.service("uploadService", function ($http) {




    //文件上传
    this.uploadFile = function () {
        //html提供的表单数据对象
        var formData=new FormData();
        //参数一:表单文件对象提交值名称与后端接受文件的名称相同
        //参数二:要提交的文件对象
        formData.append("file",file.files[0]);
        return $http({
            method:'post',
            url:"../upload/uploadFile.do",
            data:formData,
            headers:{'Content-Type':undefined},
            transformRequest : angular.identity
        })

    }


})