<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>课程管理</title>
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
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">课程编号</label>
                    <div class="layui-input-inline">
                        <input name="courseNo" autocomplete="off" class="layui-input courseNo" type="text"/>
                    </div>
                    <label class="layui-form-label">课程名称</label>
                    <div class="layui-input-inline">
                        <input name="courseName" autocomplete="off" class="layui-input courseName" type="text"/>
                    </div>
                </div>
                <button class="layui-btn" lay-submit="" lay-filter="searchSubmitCourse">查询</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </form>
        <hr class="layui-bg-black"/>


        <!-- 课程列表 -->
        <table id="courseList" class="layui-hide" lay-filter="courseList"></table>
    </div>


    <!--添加或编辑用户-->
    <div id="setCourseWin" class="layer_self_wrap" style="width:660px;display:none;">
        <form id="classForm" class="layui-form layui-form-pane" method="post" action=""
              style="margin-left:10px;margin-top: 10px;">
            <input id="id" type="hidden" name="id"/>
            <div class="layui-form-item">
                <label class="layui-form-label">课程编号</label>
                <div class="layui-input-inline">
                    <input id="courseNo" name="courseNo" class="layui-input" lay-verify="required" maxlength="20" autocomplete="off" type="text"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">课程名称</label>
                <div class="layui-input-inline">
                    <input id="courseName" name="courseName" class="layui-input" lay-verify="required" maxlength="50" autocomplete="off" type="text"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">任课老师</label>
                <div class="layui-input-inline">
                    <select name="teacherId" id="courseTeacherId" lay-search="">
                        <option value=''>请选择</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入内容" id="courseRemark" maxlength="255" name="courseRemark"
                              class="layui-textarea"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="margin-left: 10px;">
                    <button class="layui-btn" lay-submit="" lay-filter="courseSubmit">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>


    <!-- 分配学生窗口 -->
    <div id="assignStuWin" class="layer_self_wrap" style="width:660px;display:none;">
        <div id="assignStuWinTransfer" class="demo-transfer" style="padding: 20px;"></div>
        <div class="layui-btn-container">
            <button type="button" class="layui-btn" lay-demotransferactive="getSelectStuData"
                    style="margin-left: 20px;margin-bottom: 10px;">确定
            </button>
            <button type="button" class="layui-btn" lay-demotransferactive="getSelectStuDataCancel"
                    style="margin-left: 20px;margin-bottom: 10px;">取消
            </button>
        </div>
    </div>


    <script type="text/html" id="courseToolbar">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="addCourse">新增</button>
            <button class="layui-btn layui-btn-sm" lay-event="deleteCourseBatch">批量删除</button>
            <button class="layui-btn layui-btn-sm" lay-event="importCourseBatch" id="importCourseBatchId">批量导入</button>
            <button class="layui-btn layui-btn-sm" lay-event="downCourseTemplate">导入模板下载</button>
        </div>
    </script>

    <!-- 课程列表中的工具栏 -->
    <script type="text/html" id="courseRowBar">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>

    <script src="${basePath}/static/js/courseManage/courseList.js"></script>

</div>
</body>
</html>