<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>菜单树</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="${basePath}/static/layui/css/layui.css" media="all" rel="stylesheet"/>
    <link href="${basePath}/static/ztree/css/metroStyle/metroStyle.css"  media="all" rel="stylesheet"/>
    <link href="${basePath}/static/ztree/css/demo.css"  media="all" rel="stylesheet"/>
</head>
<body class="layui-layout-body">

<script src="${basePath}/static/layui/layui.js" charset="utf-8"></script>
<script src="${basePath}/static/jquery/jquery-3.2.1.js" type="text/javascript"></script>
<script src="${basePath}/static/jquery/jquery-3.2.1.minxiegl.js" type="text/javascript"></script>
<script src="${basePath}/static/ztree/js/jquery.ztree.all.js" type="text/javascript"></script>

<script>

    layui.use(['layer', 'form','table'], function(){
        var layer = layui.layer;
        initTree();
    });

    function initTree() {
        var setting = {	};
        var zNodes = [];
        $.ajax({
            type: "POST",
            url: '${basePath}/menu/findMenuTree?pId=0',
            async: false,
            dataType: 'json',
            error: function(result) {
                layer.alert("与服务器连接失败");
            },
            success: function(data) {
                zNodes = data;
                $.fn.zTree.init($("#treeDemo"),setting ,zNodes);
            }
        });
    }
</script>

<div style="margin-left: 50px">
    <h3>菜单</h3>
    <ul id="treeDemo" class="ztree"></ul>
</div>

</body>
</html>