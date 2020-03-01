<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>班级管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${basePath}/static/layui-v2.5.4/layui/css/layui.css" media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body class="layui-layout-body">
<script src="${basePath}/static/layui-v2.5.4/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${basePath}/static/jquery/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${basePath}/static/jquery/jquery-3.2.1.minxiegl.js"></script>

<div class="layui-layout layui-layout-admin">

    <div style="margin-left: 10px">
        <form id="classSearch" class="layui-form layui-form-pane" method="post" action="" style="margin-top: 5px;">
            <div class="layui-form-item" style="margin-bottom: 0px">
                <div class="layui-inline">
                    <label class="layui-form-label">班级编号</label>
                    <div class="layui-input-inline">
                        <input name="classNo" autocomplete="off" class="layui-input classNo" type="text"/>
                    </div>
                    <label class="layui-form-label">班级名称</label>
                    <div class="layui-input-inline">
                        <input name="className" autocomplete="off" class="layui-input className" type="text"/>
                    </div>
                </div>
                <button class="layui-btn" lay-submit="" lay-filter="searchSubmitClass">查询</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </form>
        <hr class="layui-bg-black"/>

        <!-- 班级列表 -->
        <table id="classList" class="layui-hide" lay-filter="classList"></table>
    </div>


    <!--添加或编辑班级-->
    <div id="setClassWin" class="layer_self_wrap" style="width:660px;display:none;">
        <form id="classForm" class="layui-form layui-form-pane" method="post" action=""
              style="margin-left:10px;margin-top: 10px;">
            <input id="id" type="hidden" name="id"/>
            <div class="layui-form-item">
                <label class="layui-form-label">班级编号</label>
                <div class="layui-input-inline">
                    <input id="classNo" name="classNo" class="layui-input" lay-verify="required" maxlength="20" autocomplete="off" type="text"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">班级名称</label>
                <div class="layui-input-inline">
                    <input id="className" name="className" class="layui-input" lay-verify="required" maxlength="20" autocomplete="off" type="text"/>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入内容" id="classRemark" maxlength="255" name="classRemark" class="layui-textarea"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="margin-left: 10px;">
                    <button class="layui-btn" lay-submit="" lay-filter="classSubmit">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>


    <!-- 分配学生窗口 -->
    <div id="assignStuWin" class="layer_self_wrap" style="width:550px;display:none;">
        <div id="assignStuWinTransfer" class="demo-transfer" style="padding: 10px;"></div>
        <div class="layui-btn-container">
            <button type="button" class="layui-btn" lay-demotransferactive="getSelectStuData"
                    style="margin-left: 20px;margin-bottom: 10px;">确定
            </button>
            <button type="button" class="layui-btn" lay-demotransferactive="getSelectStuDataCancel"
                    style="margin-left: 20px;margin-bottom: 10px;">取消
            </button>
        </div>
    </div>


    <script type="text/html" id="classToolbar">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="addClass">新增</button>
            <button class="layui-btn layui-btn-sm" lay-event="deleteClassBatch">批量删除</button>
            <button class="layui-btn layui-btn-sm" lay-event="importClassBatch" id="importClassBatchId">批量导入</button>
            <button class="layui-btn layui-btn-sm" lay-event="downClassTemplate">导入模板下载</button>
            <button class="layui-btn layui-btn-sm" lay-event="exportClassBatch">批量导出</button>
            <button class="layui-btn layui-btn-sm" lay-event="assignStuBatch">分配学生</button>
        </div>
    </script>

    <!-- 班级列表中的工具栏 -->
    <script type="text/html" id="classRowBar">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>

    <script src="${basePath}/static/js/classManage/classList.js"></script>

</div>
</body>
</html>