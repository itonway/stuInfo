<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>角色管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${basePath}/static/layui/css/layui.css" media="all"/>
    <link href="${basePath}/static/ztree/css/metroStyle/metroStyle.css" media="all" rel="stylesheet"/>
    <link href="${basePath}/static/ztree/css/demo.css" media="all" rel="stylesheet"/>
</head>
<body class="layui-layout-body">
<script src="${basePath}/static/layui/layui.js" charset="utf-8"></script>
<script src="${basePath}/static/jquery/jquery-3.2.1.js" type="text/javascript"></script>
<script src="${basePath}/static/jquery/jquery-3.2.1.minxiegl.js" type="text/javascript"></script>
<script src="${basePath}/static/ztree/js/jquery.ztree.all.js" type="text/javascript"></script>
<div class="layui-layout layui-layout-admin">

    <script type="text/html" id="roleToolbar">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="addRole">新增</button>
            <button class="layui-btn layui-btn-sm" lay-event="deleteRoleBath">批量删除</button>
        </div>
    </script>

    <div style="margin-left: 10px">
        <form id="roleSearch" class="layui-form layui-form-pane" method="post" action="" style="margin-top: 5px;">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">角色名称</label>
                    <div class="layui-input-inline">
                        <input id="name" name="name" autocomplete="off" class="layui-input" type="text"/>
                    </div>
                </div>
                <button class="layui-btn" lay-submit="" lay-filter="searchSubmitRole">查询</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </form>
        <hr class="layui-bg-black"/>
        <!-- 用户列表 -->
        <table id="roleList" class="layui-hide" lay-filter="roleList"></table>
    </div>

    <!--添加或编辑用户-->
    <div id="setRoleWin" class="layer_self_wrap" style="width:600px;display:none;">
        <form id="userForm" class="layui-form layui-form-pane" method="post" action=""
              style="margin-left:10px;margin-top: 5px;">
            <input id="id" type="hidden" name="id"/>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">角色名称</label>
                <div class="layui-input-inline">
                    <input id="sysRoleName" name="name" lay-verify="name|required" maxlength="50" autocomplete="off"
                           class="layui-input" type="text"/>
                </div>
            </div>
            <div class="layui-form-item layui-form-text" style="margin-bottom: 3px">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block" style="min-height: 70px;">
                    <textarea placeholder="请输入内容" maxlength="255" id="remark" name="remark" class="layui-textarea"
                              style="min-height: 70px;"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">菜单选择</label>
                <div class="layui-input-inline" style="max-height: 300px;">
                    <!-- http://acmeworker.com/BlogContent?type=1038
                    //https://blog.csdn.net/xianglikai1/article/details/79032278 -->
                    <ul id="menuTree" class="ztree" style="max-height: 200px;margin-left: 5px;margin-top: 0px"></ul>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="margin-left: 10px;margin-top: -10px;">
                    <button class="layui-btn" lay-submit="" lay-filter="roleSubmit">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>

    <!-- 用户列表中的工具栏 -->
    <script type="text/html" id="roleRowBar">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>

    <!-- 用户列表信息 -->
    <script src="${basePath}/static/js/roleManage/roleList.js"></script>

</div>
</body>
</html>