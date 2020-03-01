<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link rel="stylesheet" href="${basePath}/static/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${basePath}/static/x-admin/css/font.css">
    <link rel="stylesheet" href="${basePath}/static/x-admin/css/xadmin.css">
    <link rel="stylesheet" href="${basePath}/static/main/css/global.css">
    <link rel="stylesheet" href="${basePath}/static/main/css/main.css">
    <link rel="stylesheet" href="${basePath}/static/main/css/backstage.css">
    <script type="text/javascript" src="${basePath}/static/layui/layui.all.js"></script>
    <script type="text/javascript" src="${basePath}/static/jquery/jquery-3.2.1.minxiegl.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/mainManage/main.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/mainManage/bodyTab.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/mainManage/common.js"></script>

</head>
<body class="main_body larryTheme-A">

<div class="layui-layout layui-layout-admin ">
    <!-- 顶部-->
    <div class="layui-header header header-menu ">
        <div class="layui-main ">
            <a href="#" class="logo">客户管理系统</a>
            <!-- 左侧导航收缩开关 -->
            <div class="side-menu-switch" id="toggle"><span class="switch" ara-hidden="true"></span></div>
            <!-- 顶级菜单 -->
            <div class="larry-top-menu posb topMenu" id="topMenu"></div>
            <!-- 右侧常用菜单导航 -->
            <div class="larry-right-menu posb">
                <ul class="layui-nav clearfix ">
                    <li class="layui-nav-item exit">
                        <a id="logout"><i class="larry-icon larry-exit"></i><cite>退出</cite></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <!-- 左侧导航-->
    <div class="layui-side layui-bg-black">
        <div class="user-photo">
            <a class="img" title="我的头像"><img src="${basePath}/static/image/face.jpg"></a>
            <p>你好！<span class="userName" id="userNameSpan" title="">解国亮</span>, 欢迎登录</p>
        </div>
        <!-- 左侧菜单-->
        <div class="navBar layui-side-scroll" id="navBarId"></div>

    </div>
    <!--中间内容 -->
    <div class="layui-body layui-form" id="larry-body">
        <div class="layui-tab marg0" id="larry-tab" lay-filter="bodyTab">
            <! -- 选项卡-->
            <ul class="layui-tab-title top_tab" id="top_tabs">
                <li class="layui-this" lay-id=""><i class="larry-icon larry-houtaishouye"></i> <cite>后台首页</cite></li>
            </ul>
            <div class="larry-title-box" style="height: 41px;">
                <div class="go-left key-press pressKey" id="titleLeft" title="滚动至最右侧"><i
                            class="larry-icon larry-weibiaoti6-copy"></i></div>
                <div class="title-right" id="titleRbox">
                    <div class="go-right key-press pressKey" id="titleRight" title="滚动至最左侧"><i
                                class="larry-icon larry-right"></i></div>
                    <div class="refresh key-press" id="refresh_iframe"><i
                                class="larry-icon larry-shuaxin2"></i><cite>刷新</cite></div>

                    <div class="often key-press">
                        <ul class="layui-nav posr">
                            <li class="layui-nav-item posb">
                                <a class="top"><i class="larry-icon larry-caozuo"></i><cite>常用操作</cite><span
                                            class="layui-nav-more"></span></a>
                                <dl class="layui-nav-child">
                                    <dd>
                                        <a href="javascript:;" class="closeCurrent"><i
                                                    class="larry-icon larry-guanbidangqianye"></i>关闭当前选项卡</a>
                                    </dd>
                                    <dd>
                                        <a href="javascript:;" class="closeOther"><i
                                                    class="larry-icon larry-guanbiqita"></i>关闭其他选项卡</a>
                                    </dd>
                                    <dd>
                                        <a href="javascript:;" class="closeAll"><i
                                                    class="larry-icon larry-guanbiquanbufenzu"></i>关闭全部选项卡</a>
                                    </dd>
                                </dl>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 底部-->
    <div class="layui-footer footer layui-larry-foot">
        <div class="layui-main">
            <p>欢迎使用客户管理系统</p>
        </div>
    </div>
</div>

</body>
</html>
