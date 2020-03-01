<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生信息管理系统</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link rel="stylesheet" href="${basePath}/static/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${basePath}/static/x-admin/css/font.css">
    <link rel="stylesheet" href="${basePath}/static/x-admin/css/xadmin.css">
    <script type="text/javascript" src="${basePath}/static/layui/layui.all.js"></script>
    <script type="text/javascript" src="${basePath}/static/jquery/jquery-3.2.1.minxiegl.js"></script>

</head>
<body class="login-bg">

<div class="login">
    <div class="message">学生信息管理系统</div>
    <div id="darkbannerwrap"></div>
    <form method="post"  class="layui-form" >
        <input name="loginName" placeholder="用户名" autocomplete="off"  type="text" lay-verify="loginName" class="layui-input" >
        <hr class="hr15">
        <input name="password" lay-verify="password" placeholder="密码" autocomplete="off"  type="password" class="layui-input">
        <hr class="hr15">
        <div  class="layui-inline">
            <label class="layui-form-label" style="width:40px;padding: 9px 0px;">验证码&nbsp;</label>
            <div class="layui-input-inline">
                <input type="text" name="code" style="width:150px;height:35px;" autocomplete="off" lay-verify="code"   class="layui-input">
            </div>
            <div class="layui-input-inline">
                <img src="" id="code">
            </div>
        </div>
        <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</div>
<script>
    $(function  () {
        layui.use(['form','layer'], function(){
            let form = layui.form;
            let layer = layui.layer;
            form.verify({
                loginName:function(v){
                    if(v.trim( ) == ''){
                        return "用户名不能为空";
                    }
                },
                password:function(v){
                    if(v.trim() == ''){
                        return "密码不能为空";
                    }
                },
                code:function(v){
                    if(v.trim() == ''){
                        return '验证码不能为空';
                    }
                }
            });


            form.on('submit(login)', function(obj){
                let ajaxReturnData = null;
                let loginLoading = top.layer.msg('登陆中，请稍候', {icon: 16, time: false, shade: 0.8});
                $.ajax({
                    url: '${basePath}/login/loginSystem',
                    type: 'POST',
                    async: false,
                    data: obj.field,
                    success: function (data) {
                        ajaxReturnData = data;
                    },
                    fail:function (data) {
                        console.info("登陆的结果："+data);
                    }
                });

                if (ajaxReturnData != null && ajaxReturnData.returnCode == "0000") {
                    window.location.href = "${basePath}/login/jumpIndex";
                    layer.close(loginLoading);
                    return false;
                } else {
                    layer.msg(ajaxReturnData.returnMessage ,{time: 3000});
                    layer.close(loginLoading);
                    return false;
                }
            });

            form.render();
        });


        $("#code").click(function(){
            let url = "../login/getCode?"+new Date().getTime();
            this.src = url;
        }).click().show();

        $('#code').on('mouseover',function(){
            layer.tips('点击刷新验证码', this,{time:1000});
        });

    });

</script>
</body>
</html>
