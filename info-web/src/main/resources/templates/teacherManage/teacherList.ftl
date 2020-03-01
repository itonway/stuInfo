<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>教师管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${basePath}/static/layui/css/layui.css" media="all">
</head>
<body class="layui-layout-body">
<script src="${basePath}/static/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${basePath}/static/jquery/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${basePath}/static/jquery/jquery-3.2.1.minxiegl.js"></script>

<div class="layui-layout layui-layout-admin">

    <div style="margin-left: 10px">
        <form id="teacherSearch" class="layui-form layui-form-pane" method="post" action="" style="margin-top: 5px;">
            <div class="layui-form-item" style="margin-bottom: 0px">
                <div class="layui-inline">
                    <label class="layui-form-label">教师姓名</label>
                    <div class="layui-input-inline">
                        <input name="teacherName" autocomplete="off" class="layui-input teacherName" type="text"/>
                    </div>
                    <label class="layui-form-label" style="display:block;float:left">性别</label>
                    <div class="layui-input-inline">
                        <select name="teacherSex" id="sexSerachTeacher" lay-search="">
                            <option value=''>请选择</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: -5px">
                <div class="layui-inline">
                    <label class="layui-form-label">入职日期</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="startJobDate" id="teacherCreateTime">
                    </div>
                </div>
                <button class="layui-btn" lay-submit="" lay-filter="searchSubmitTeacher">查询</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </form>
        <hr class="layui-bg-black"/>

        <script type="text/html" id="teacherToolbar">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-sm" lay-event="addTeacher">新增</button>
                <button class="layui-btn layui-btn-sm" lay-event="deleteTeacherBatch">批量删除</button>
                <button class="layui-btn layui-btn-sm" lay-event="importTeacherBatch" id="importTeacherBatchId">批量导入
                </button>
                <button class="layui-btn layui-btn-sm" lay-event="downTeacherTemplate">导入模板下载</button>
                <button class="layui-btn layui-btn-sm" lay-event="exportTeacherBatch">批量导出</button>
            </div>
        </script>

        <!--教师列表 -->
        <table id="teacherList" class="layui-hide" lay-filter="teacherList"></table>
    </div>


    <!--添加或编辑用户-->
    <div id="setTeacherWin" class="layer_self_wrap" style="width:660px;display:none;">
        <form id="teacherForm" class="layui-form layui-form-pane" method="post" action=""
              style="margin-left:10px;margin-top: 5px;">
            <input id="id" type="hidden" name="id"/>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">教师编号</label>
                <div class="layui-input-inline">
                    <input id="teacherNo" name="teacherNo" class="layui-input" lay-verify="required" maxlength="20" autocomplete="off" type="text"/>
                </div>
                <label class="layui-form-label">教师姓名</label>
                <div class="layui-input-inline">
                    <input id="teacherName" name="teacherName" class="layui-input" lay-verify="required" maxlength="20" autocomplete="off" type="text"/>
                </div>

            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-inline">
                    <input type="radio" name="teacherSex" value="1" title="男" checked="">
                    <input type="radio" name="teacherSex" value="2" title="女">
                </div>
                <label class="layui-form-label">出生年月</label>
                <div class="layui-input-inline">
                    <input type="text" name="teacherBirthDate" id="teacherBirthDate" placeholder="yyyy-MM-dd"
                           autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline">
                    <input id="teacherMobile" name="teacherMobile" maxlength="11" lay-verify="required|phone" autocomplete="off" class="layui-input" type="text"/>
                </div>
                <label class="layui-form-label">身份证号</label>
                <div class="layui-input-inline">
                    <input id="teacherCard" name="teacherCard" maxlength="18" lay-verify="teacherCard|required" class="layui-input"
                           autocomplete="off" type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                    <input id="teacherEmail" name="teacherEmail" maxlength="20" lay-verify="required|email" autocomplete="off" class="layui-input" type="text"/>
                </div>
                <label class="layui-form-label">入职日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="startJobDate" id="teacherStartJobDate" lay-verify="required" placeholder="yyyy-MM-dd"
                           autocomplete="off"
                           class="layui-input ">
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">离职日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="endJobDate" id="teacherEndJobDate" placeholder="yyyy-MM-dd"
                           autocomplete="off"
                           class="layui-input ">
                </div>
                <label class="layui-form-label">家庭住址</label>
                <div class="layui-input-inline">
                    <input id="teacherAddress" name="teacherAddress" maxlength="255" autocomplete="off" class="layui-input"
                           type="text"/>
                </div>
            </div>
            <div class="layui-form-item layui-form-text" style="margin-bottom: 3px">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入内容" id="teacherRemark" maxlength="255" name="teacherRemark"
                              class="layui-textarea" style="min-height: 70px;"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="margin-left: 10px;">
                    <button class="layui-btn" lay-submit="" lay-filter="teacherSubmit">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>


    <!-- 教师列表中的工具栏 -->
    <script type="text/html" id="teacherRowBar">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>

    <script src="${basePath}/static/js/teacherManage/teacherList.js"></script>

</div>
</body>
</html>