<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>成绩管理</title>
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
                    <label class="layui-form-label" style="display:block;float:left">所属班级</label>
                    <div class="layui-input-inline">
                        <select name="classId" id="scoreClassIdSerach" lay-search="">
                            <option value=''>请选择</option>
                        </select>
                    </div>
                    <label class="layui-form-label">学生姓名</label>
                    <div class="layui-input-inline">
                        <select name="stuId" id="scoreStuIdSerach" lay-search="">
                            <option value=''>请选择</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: -5px">
                <label class="layui-form-label">课程名称</label>
                <div class="layui-input-inline">
                    <select name="courseId" id="scoreCourseIdSearch" lay-search="">
                        <option value=''>请选择</option>
                    </select>
                </div>
                <button class="layui-btn" lay-submit="" lay-filter="searchSubmitScore">查询</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </form>
        <hr class="layui-bg-black"/>

        <script type="text/html" id="scoreToolbar">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-sm" lay-event="addScore">新增</button>
                <button class="layui-btn layui-btn-sm" lay-event="deleteScoreBatch">批量删除</button>
                <button class="layui-btn layui-btn-sm" lay-event="importScoreBatch" id="importScoreBatchId">批量导入
                </button>
                <button class="layui-btn layui-btn-sm" lay-event="downScoreTemplate">导入模板下载</button>
                <button class="layui-btn layui-btn-sm" lay-event="exportScoreBatch">批量导出</button>
            </div>
        </script>

        <!-- 分数列表 -->
        <table id="scoreList" class="layui-hide" lay-filter="scoreList"></table>
    </div>


    <!--添加或编辑用户-->
    <div id="setScoreWin" class="layer_self_wrap" style="width:660px;display:none;">
        <form id="scoreForm" class="layui-form layui-form-pane" method="post" action=""
              style="margin-left:10px;margin-top: 5px;">
            <input id="id" type="hidden" name="id"/>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">学生姓名</label>
                <div class="layui-input-inline">
                    <select name="stuId" id="scoreStuId" lay-verify="required" lay-search="">
                        <option value=''>请选择</option>
                    </select>
                </div>
                <label class="layui-form-label">课程名称</label>
                <div class="layui-input-inline">
                    <select name="courseId" id="scoreCourseId" lay-verify="required" lay-search="">
                        <option value=''>请选择</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">分数</label>
                <div class="layui-input-inline">
                    <input id="score" name="score" autocomplete="off" maxlength="3" class="layui-input" type="text"
                           lay-verify="score|required|number"/>
                </div>
            </div>
            <div class="layui-form-item layui-form-text" style="margin-bottom: 3px">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block" style="min-height: 70px;">
                    <textarea placeholder="请输入内容" id="scoreRemark" maxlength="255" name="scoreRemark" class="layui-textarea"
                              style="min-height: 70px;"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="margin-left: 10px;">
                    <button class="layui-btn" lay-submit="" lay-filter="scoreSubmit">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>


    <!-- 用户列表中的工具栏 -->
    <script type="text/html" id="scoreRowBar">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>

    <script src="${basePath}/static/js/scoreManage/scoreList.js"></script>

</div>
</body>
</html>