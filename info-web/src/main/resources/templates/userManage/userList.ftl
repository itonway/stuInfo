<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${basePath}/static/layui/css/layui.css" media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body class="layui-layout-body">
<script src="${basePath}/static/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${basePath}/static/jquery/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${basePath}/static/jquery/jquery-3.2.1.minxiegl.js"></script>

<div class="layui-layout layui-layout-admin">

    <div style="margin-left: 10px">
        <form id="userSearch" class="layui-form layui-form-pane" method="post" action="" style="margin-top: 5px;">
            <div class="layui-form-item" style="margin-bottom: 0px">
                <div class="layui-inline">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-inline">
                        <input id="sysUserName" name="userName" autocomplete="off" class="layui-input" type="text"/>
                    </div>
                    <label class="layui-form-label" style="display:block;float:left">性别</label>
                    <div class="layui-input-inline">
                        <select name="sex" id="sex" lay-search="">
                            <option value=''>请选择</option>
                        </select>
                    </div>
                    <button class="layui-btn" lay-submit="" lay-filter="searchSubmit">查询</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
        <hr class="layui-bg-black"/>

        <script type="text/html" id="userToolbar">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-sm" lay-event="addUser">新增</button>
                <button class="layui-btn layui-btn-sm" lay-event="deleteUserBatch">批量删除</button>
                <button class="layui-btn layui-btn-sm" lay-event="importUserBatch" id="importUserBatch">批量导入</button>
                <button class="layui-btn layui-btn-sm" lay-event="downUserTemplate">导入模板下载</button>
            </div>
        </script>


        <!-- 用户列表 -->
        <table id="userList" class="layui-hide" lay-filter="userList"></table>
    </div>


    <!--添加或编辑用户-->
    <div id="setUserWin" class="layer_self_wrap" style="width:660px;display:none;">
        <form id="userForm" class="layui-form layui-form-pane" method="post" action=""
              style="margin-left:10px;margin-top: 10px;">
            <input id="id" type="hidden" name="id"/>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                    <input id="userName" name="userName" lay-verify="userName|required" maxlength="50"
                           autocomplete="off"
                           class="layui-input" type="text"/>
                </div>
                <label class="layui-form-label">登录名</label>
                <div class="layui-input-inline">
                    <input id="loginName" name="loginName" lay-verify="loginName|required" maxlength="10"
                           autocomplete="off"
                           class="layui-input" type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">登陆密码</label>
                <div class="layui-input-inline">
                    <input id="password" name="password" lay-verify="required|password" maxlength="10"
                           autocomplete="off" class="layui-input"
                           type="password"/>
                </div>
                <label class="layui-form-label">性别</label>
                <div class="layui-input-inline">
                    <input type="radio" name="sex" value="1" title="男" checked="">
                    <input type="radio" name="sex" value="2" title="女">
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                    <input id="email" name="email" autocomplete="off" lay-verify="email" class="layui-input"
                           maxlength="50" type="text"/>
                </div>
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline">
                    <input id="mobile" name="mobile" autocomplete="off" class="layui-input" maxlength="11"
                           lay-verify="required|phone" type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px;">
                <label class="layui-form-label">角色选择</label>
                <div class="layui-input-inline" id="rolecheckboxlist"
                     style="min-height: 200px;max-height: 200px;min-width:500px;overflow-y:scroll"></div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="margin-left: 10px;">
                    <button class="layui-btn" lay-submit="" lay-filter="userSubmit">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>

    <!-- 用户列表中的工具栏 -->
    <script type="text/html" id="userRowBar">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>

    <!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
    <!-- 用户列表信息 -->
    <script src="${basePath}/static/js/userManage/userList.js"></script>
    <script>
        layui.use('laydate', function () {
            let laydate = layui.laydate;
            let myNewDate = new Date();
            myNewDate.setDate(myNewDate.getDate() - 30);//30天前的日期
            let myDate = new Date();
            laydate.render({
                elem: '#createTimeStr'
                , type: 'datetime'
                , value: myNewDate
            });
            laydate.render({
                elem: '#createTimeEnd'
                , type: 'datetime'
                , value: myDate
            });
        });


        layui.use('form', function () {
            let form = layui.form;
            $.ajax({
                url: '${basePath}/dict/findDictItemListByDictCode',
                dataType: 'json',
                type: 'post',
                data: {
                    "dictCode": "1001"
                },
                success: function (data) {
                    $.each(data.data, function (index, item) {
                        $('#sex').append(new Option(item.dictItemName, item.dictItemCode));
                    });
                    form.render();
                }
            })
        });


    </script>
</div>
</body>
</html>