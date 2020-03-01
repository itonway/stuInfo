<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>部门列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../static/layui/css/layui.css" media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>

<!-- 用户列表 -->
<table class="layui-hide" id="userList" ></table>
<!-- 用户列表头部工具栏 -->
<script type="text/html" id="userToolBar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
        <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
    </div>
</script>
<!-- 用户列表中的工具栏 -->
<script type="text/html" id="userRowBar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script src="../../static/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#userList'
            , url: '../user/findUserList'
            , toolbar: '#userToolBar'
            , title: '用户数据表'
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', width: 80}
                , {field: 'userName', title: '用户名', width: 120}
                , {field: 'sex', title: '性别', width: 80,templet:function (res) {
                        let sexStr = "";
                        if(res.sex == '1'){
                            sexStr = '男';
                        }else if (res.sex == '0'){
                            sexStr = '女';
                        }else{
                            sexStr = '未知';
                        }
                        return sexStr;
                    }}
                , {field: 'email', title: '邮箱', width: 150}
                , {field: 'age', title: '年龄', width: 100}
                , {field: 'mobile', title: '手机号', width: 150}
                , {field: 'creator', title: '创建人', width: 100}
                , {field: 'createTimeStr', title: '创建时间', width: 180}
                , {fixed: 'right', title: '操作', toolbar: '#userRowBar', width: 150}
            ]]
            , page: true
        });
    });
</script>
</body>
</html>