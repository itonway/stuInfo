<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <!-- 页面描述 -->
    <meta name="description" content="网站描述"/>
    <!-- 页面关键词 -->
    <meta name="keywords" content="关键词"/>
    <!-- 网页作者 -->
    <meta name="author" content="XUXY"/>
    <link rel="icon" href="http://ow9lg82yy.bkt.clouddn.com/24f5c360-485d-4a6d-9468-2a61c353cf37.ico">
    <link rel="stylesheet" href="${basePath}/static/layui-v2.5.4/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all"/>
    <link rel="stylesheet" href="${basePath}/static/css/main.css" media="all"/>
</head>
<body class="main_body">
<div class="layui-layout layui-layout-admin">
    <!-- 顶部 -->
    <div class="layui-header header">
        <div class="layui-main">
            <a href="#" class="logo">学生管理系统</a>
            <!-- 显示/隐藏菜单 -->
            <a href="javascript:" class="iconfont hideMenu icon-menu1"></a>
            <!-- 顶部右侧菜单 -->
            <ul class="layui-nav top_menu">
                <li class="layui-nav-item">
                    <a href="javascript:">
                        <cite style="margin-right: 30px;">${loginUser.userName}</cite>
                    </a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:" class="personalItem">
                                <i class="iconfont icon-zhanghu" data-icon="icon-zhanghu"></i>
                                <cite>个人资料</cite>
                            </a>
                        </dd>
                        <dd>
                            <a href="javascript:" class="changePwd">
                                <i class="iconfont icon-shezhi1" data-icon="icon-shezhi1"></i>
                                <cite>修改密码</cite>
                            </a>
                        </dd>
                        <dd>
                            <a href="javascript:" class="signOut">
                                <i class="iconfont icon-loginout"></i>
                                <cite>退出</cite>
                            </a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <!-- 左侧导航 -->
    <div class="layui-side layui-bg-black">
        <div class="user-photo">
            <a class="img" title="我的头像">
                <img src="${basePath}/static/image/face.jpg">
            </a>
            <p>你好！${loginUser.userName}，欢迎登录</p>
        </div>
        <div class="navBar layui-side-scroll"></div>
    </div>
    <!-- 右侧内容 -->
    <div class="layui-body layui-form">
        <div class="layui-tab marg0" lay-filter="bodyTab" id="top_tabs_box">
            <ul class="layui-tab-title top_tab" id="top_tabs">
                <li class="layui-this" lay-id=""><i class="iconfont icon-computer"></i> <cite>后台首页</cite></li>
            </ul>
            <ul class="layui-nav closeBox">
                <li class="layui-nav-item">
                    <a href="javascript:"><i class="iconfont icon-caozuo"></i> 页面操作</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:" class="refresh refreshThis"><i class="layui-icon">&#x1002;</i>
                                刷新当前</a></dd>
                        <dd><a href="javascript:" class="closePageOther"><i class="iconfont icon-prohibit"></i> 关闭其他</a>
                        </dd>
                        <dd><a href="javascript:" class="closePageAll"><i class="iconfont icon-guanbi"></i> 关闭全部</a>
                        </dd>
                    </dl>
                </li>
            </ul>
            <div class="layui-tab-content clildFrame">
                <div class="layui-tab-item layui-show">
                    <iframe src="${basePath}/login/jumpMain"></iframe>
                </div>
            </div>
        </div>
    </div>
    <!-- 底部 -->
    <div class="layui-footer footer">
        <p>学生信息管理系统</p>
    </div>

    <!--个人资料-->
    <div id="personalItemWin" class="layer_self_wrap" style="width:660px;display:none;">
        <form id="userForm" class="layui-form layui-form-pane" method="post" action=""
              style="margin-left:10px;margin-top: 10px;">
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                    <input id="personalItemUserName" name="userName" autocomplete="off"
                           class="layui-input layui-disabled" type="text"/>
                </div>
                <label class="layui-form-label">登录名</label>
                <div class="layui-input-inline">
                    <input id="personalItemLoginName" name="loginName" autocomplete="off"
                           class="layui-input layui-disabled" type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">登陆密码</label>
                <div class="layui-input-inline">
                    <input id="personalItemPassword" name="password" autocomplete="off"
                           class="layui-input layui-disabled" type="password"/>
                </div>
                <label class="layui-form-label">性别</label>
                <div class="layui-input-inline">
                    <input id="personalItemSex" name="sex" autocomplete="off" class="layui-input layui-disabled"
                           type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                    <input id="personalItemEmail" name="email" class="layui-input layui-disabled" type="text"/>
                </div>
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline">
                    <input id="personalItemMobile" name="mobile" autocomplete="off" class="layui-input layui-disabled"
                           type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">用户角色</label>
                <#--
                                <div class="layui-input-inline layui-disabled" id="personalItemRolecheckboxlist"></div>
                -->
                <div class="layui-input-inline" style="max-height: 200px;min-width: 500px;overflow-y:scroll"
                ">
                    <ul id="personalItemRolecheckboxlist" style="max-height: 200px;"></ul>
                </div>
            </div>
        </form>
    </div>


    <!-- 重置密码 -->
    <div id="changePwdWin" class="layer_self_wrap" style="width:400px;display:none;">
        <form id="changePwdForm" class="layui-form layui-form-pane" method="post" action=""
              style="margin-left:10px;margin-top: 10px;">
            <input id="id" type="hidden" name="id"/>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                    <input id="changePwdUserName" name="changePwdUserName" autocomplete="off" class="layui-input"
                           type="text"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">旧密码</label>
                <div class="layui-input-inline">
                    <input id="oldPwd" name="oldPwd" autocomplete="off" lay-verify="required" class="layui-input"
                           type="password"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">新密码</label>
                <div class="layui-input-inline">
                    <input id="newPwd" name="newPwd" autocomplete="off" lay-verify="required" class="layui-input"
                           type="password"/>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 3px">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-inline">
                    <input id="confirmNewPwd" name="confirmNewPwd" lay-verify="required" class="layui-input"
                           type="password"/>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="margin-left: 0px;">
                    <button class="layui-btn" lay-submit="" lay-filter="changePwdSubmit">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>

        </form>
    </div>


</div>
<script>
    var baseUrl = "${basePath}";
</script>
<!-- 移动导航 -->
<div class="site-tree-mobile layui-hide"><i class="layui-icon">&#xe602;</i></div>
<div class="site-mobile-shade"></div>

<script type="text/javascript" src="${basePath}/static/layui-v2.5.4/layui/layui.js"></script>
<script type="text/javascript" src="${basePath}/static/js/mainManageNew/leftNav.js"></script>
<script type="text/javascript" src="${basePath}/static/js/mainManageNew/index.js"></script>
</body>
</html>