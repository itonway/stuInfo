<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生管理</title>
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
                    <label class="layui-form-label">学生姓名</label>
                    <div class="layui-input-inline">
                        <input name="stuName" autocomplete="off" class="layui-input stuName" type="text"/>
                    </div>
                    <label class="layui-form-label" style="display:block;float:left">性别</label>
                    <div class="layui-input-inline">
                        <select name="stuSex" id="sexSerach" lay-search="">
                            <option value=''>请选择</option>
                        </select>
                    </div>

                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: -5px">
                <label class="layui-form-label" style="display:block;float:left">所属班级</label>
                <div class="layui-input-inline">
                    <select name="classId" id="classNoSerach" lay-search="">
                        <option value=''>请选择</option>
                    </select>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">入学日期</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="stuStartDate" id="stuStartDateSerach">
                    </div>
                </div>
                <button class="layui-btn" lay-submit="" lay-filter="searchSubmitStu">查询</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </form>
        <hr class="layui-bg-black"/>

        <script type="text/html" id="stuToolbar">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-sm" lay-event="addStu">新增</button>
                <button class="layui-btn layui-btn-sm" lay-event="deleteStuBatch">批量删除</button>
                <button class="layui-btn layui-btn-sm" lay-event="importStuBatch" id="importStuBatchId">批量导入</button>
                <button class="layui-btn layui-btn-sm" lay-event="downStuTemplate">导入模板下载</button>
                <button class="layui-btn layui-btn-sm" lay-event="exportStuBatch">批量导出</button>
            </div>
        </script>

        <!-- 用户列表 -->
        <table id="stuList" class="layui-hide" lay-filter="stuList"></table>
    </div>


    <!--添加或编辑用户-->
    <div id="setStuWin" class="layer_self_wrap" style="width:660px;display:none;">
        <form id="stuForm" class="layui-form layui-form-pane" method="post" action=""
              style="margin-left:10px;margin-top: 5px;">
            <input id="id" type="hidden" name="id"/>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">学生编号</label>
                <div class="layui-input-inline">
                    <input id="stuNo" name="stuNo" class="layui-input" maxlength="20" lay-verify="stuNo|required" autocomplete="off" type="text"/>
                </div>
                <label class="layui-form-label">学生姓名</label>
                <div class="layui-input-inline">
                    <input id="stuName" name="stuName" class="layui-input" maxlength="50" lay-verify="stuName|required" autocomplete="off" type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">年龄</label>
                <div class="layui-input-inline">
                    <input id="stuAge" name="stuAge" lay-verify="age|required|number" maxlength="3" autocomplete="off"
                           class="layui-input"
                           type="text"/>
                </div>
                <label class="layui-form-label">性别</label>
                <div class="layui-input-inline">
                    <input type="radio" name="stuSex" value="1" title="男" checked="">
                    <input type="radio" name="stuSex" value="2" title="女">
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">出生年月</label>
                <div class="layui-input-inline">
                    <input type="text" name="stuBirthDate" id="stuBirthDate" maxlength="20" placeholder="yyyy-MM-dd" autocomplete="off"
                           class="layui-input">
                </div>
                <label class="layui-form-label">身份证号</label>
                <div class="layui-input-inline">
                    <input id="stuCard" name="stuCard" lay-verify="stuCard|required" maxlength="18" class="layui-input"
                           autocomplete="off" type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                    <input id="stuEmail" name="stuEmail" autocomplete="off" lay-verify="required|email"  class="layui-input" type="text"/>
                </div>
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline">
                    <input id="stuMobile" name="stuMobile" autocomplete="off" lay-verify="required|phone"  class="layui-input" type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">入学日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="stuStartDate" placeholder="yyyy-MM-dd" lay-verify="required" autocomplete="off"
                           class="layui-input" id="stuStartDateAdd">
                </div>
                <label class="layui-form-label">毕业日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="stuEndDate" placeholder="yyyy-MM-dd" autocomplete="off"
                           class="layui-input" id="stuEndDateAdd">
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">班级</label>
                <div class="layui-input-inline">
                    <select name="classId" id="classId" lay-search="">
                        <option value=''>请选择</option>
                    </select>
                </div>
                <label class="layui-form-label">家庭住址</label>
                <div class="layui-input-inline">
                    <input id="stuAddress" name="stuAddress" autocomplete="off" maxlength="255" class="layui-input" type="text"/>
                </div>
            </div>
            <div class="layui-form-item layui-form-text" style="margin-bottom: 3px">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block" style="min-height: 70px;">
                    <textarea placeholder="请输入内容" id="stuRemark" name="stuRemark" maxlength="255" class="layui-textarea"
                              style="min-height: 70px;"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="margin-left: 10px;">
                    <button class="layui-btn" lay-submit="" lay-filter="stuSubmit">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>


    <!-- 用户列表中的工具栏 -->
    <script type="text/html" id="stuRowBar">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>

    <script src="${basePath}/static/js/stuManage/stuList.js"></script>

</div>
</body>
</html>