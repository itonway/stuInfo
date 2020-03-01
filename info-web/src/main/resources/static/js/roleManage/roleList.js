let zTreeObj;
let zNodes = [];
var tableInit;
let pageCurr;
var formInit;
$(function () {
    layui.use(['layer', 'form', 'table', 'upload', 'util', 'laydate'], function () {
        var $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            table = layui.table,
            t;
        t = table.render({
            elem: '#roleList',
            url: '../role/findRoleListPage',
            toolbar: '#roleToolbar',
            mothed: 'post',
            title: '角色数据表',
            height: 380,
            page: true,
            cols: [[
                {
                    type: 'checkbox',
                    fixed: 'left'
                },
                {
                    field: 'id',
                    title: 'ID'
                },
                {
                    field: 'name',
                    title: '角色名称'
                },
                {
                    field: 'remark',
                    title: '角色描述'
                },
                {
                    field: 'creater',
                    title: '创建人'
                },
                {
                    field: 'createTimeStr',
                    title: '创建时间',
                    width: 250
                },
                {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#roleRowBar'
                }
            ]],
            done: function (res, curr) {
                pageCurr = curr;
                $("[data-field='id']").css('display', 'none');
            }
        });

        table.render(t);
        tableInit = table;
        formInit = form;

        //头工具栏事件
        table.on('toolbar(roleList)', function (obj) {
            switch (obj.event) {
                case 'addRole':
                    openRoleWin(null, "新增角色");
                    break;
                case 'deleteRoleBath':
                    let checkStatus = table.checkStatus(obj.config.id);
                    let data = checkStatus.data;
                    deleteRoleBath(data, layer);
                    break;
            }
        });

        //监听工具条
        table.on('tool(roleList)', function (obj) {
            let roleData = obj.data;
            if (obj.event === 'del') {
                deleteRole(roleData);
            } else if (obj.event === 'edit') {
                openRoleWin(roleData, "编辑角色");
            }
        });


        //新增，编辑提交
        form.on('submit(roleSubmit)', function (obj) {
            console.log("接收到的参数：" + obj.field);
            saveAndUpdateRole(obj);
            return false;
        });

        //搜索 提交
        form.on("submit(searchSubmitRole)", function (data) {
            t.where = data.field;
            table.reload('roleList', t);
            return false;
        });



    });
});


//删除数据

function deleteRole(roleData) {
    if (roleData == null) {
        layer.alert("请选择要删除的数据");
        return;
    }
    let id = roleData.id;
    let name = roleData.name;
    let confirmWin = layer.confirm("确定要删除角色名称为【" + name + "】吗？", {
        btn: ['确定', '取消'],
        title: "提示"
    }, function () {
        $.post("../role/deleteById", {
            id: id
        }, function (data) {
            if (data.code == "0000") {
                layer.msg(data.message, {
                    time: 3000
                });
                layer.close(confirmWin);
                tableInit.reload("roleList");
            } else {
                layer.alert(data.message);
            }
        });
    }, function () {
        console.info("取消删除的操作");
        layer.closeAll();
    })
}


let roleWin;

function openRoleWin(roleData, title) {
    if (roleData != null) {
        $("#id").val(roleData.id);
        $("#sysRoleName").val(roleData.name);
        $("#remark").val(roleData.remark);
        initCheckedTree(roleData.id);
    } else {
        $("#id").val(""); //新增
        initTree();
    }
    roleWin = layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['800px'],
        content: $('#setRoleWin'),
        end: function () {
            cleanRole();
        }
    })
}

//保存更新

function saveAndUpdateRole(roleData) {
    let nodes = zTreeObj.getCheckedNodes(true);
    let menuArrIds = [];
    for (let i = 0; i < nodes.length; i++) {
        menuArrIds.push(nodes[i].id);
    }
    roleData.field.menuIds = menuArrIds;
    console.info("选中的菜单ID:" + menuArrIds);
    $.ajax({
        url: '../role/saveAndUpdateRole',
        dataType: 'json',
        type: 'post',
        data: roleData.field,
        success: function (result) {
            if (result.code == "0000") {
                layer.msg(result.message, {
                    time: 1000
                }, function () {
                    layer.close(roleWin);
                    tableInit.reload("roleList");
                });
            } else {
                layer.alert(result.message);
            }
        },
        fail: function (result) {
            layer.alert(result.message);
        }
    })
}

function cleanRole() {
    $("#sysRoleName").val("");
    $("#remark").val("");
}

//批量删除

function deleteRoleBath(roleData) {
    if (roleData.length <= 0) {
        layer.msg("请选择需要删除的角色", {
            time: 2000
        });
        return;
    }
    let ids = [];
    for (let i = 0; i < roleData.length; i++) {
        let roleId = roleData[i].id;
        ids.push(roleId);
    }
    layer.confirm("确定要删除这些角色吗？", {
        btn: ['确定', '取消']
    }, function () {
        $.post("../role/deleteRoleBatch", {
            ids: ids
        }, function (data) {
            if (data.code == "0000") {
                layer.msg(data.message, {
                    time: 1000
                }, function () {
                    tableInit.reload('roleList');
                });
            } else {
                layer.alert(data.message);
            }
        });
    });
}

//初始化展示菜单树
function initTree() {
    let setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {
                "Y": "p",
                "N": "s"
            },
            nocheckInherit: true,
            chkDisabledInherit: true
        }
    };

    $.ajax({
        type: "POST",
        url: '../menu/findMenuTree?pId=0',
        async: false,
        dataType: 'json',
        error: function (result) {
            layer.alert("与服务器连接失败");
        },
        success: function (data) {
            zNodes = data;
            zTreeObj = $.fn.zTree.init($("#menuTree"), setting, zNodes);
        }
    });
}


//初始化展示菜单树

function initCheckedTree(roleId) {
    let setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {
                "Y": "p",
                "N": "s"
            },
            nocheckInherit: true,
            chkDisabledInherit: true
        }
    };

    $.ajax({
        type: "POST",
        url: '../menu/findMenuTreeByRoleId?roleId=' + roleId,
        async: false,
        dataType: 'json',
        error: function (result) {
            layer.alert("与服务器连接失败");
        },
        success: function (data) {
            zNodes = data;
            zTreeObj = $.fn.zTree.init($("#menuTree"), setting, zNodes);
        }
    });
}