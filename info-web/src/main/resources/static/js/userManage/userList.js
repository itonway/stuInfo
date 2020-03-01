var tableInit;
let pageCurr;
$(function () {
    layui.use(['layer', 'form', 'table', 'upload'], function () {
        var layer = layui.layer,
            $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            upload = layui.upload,
            t;
        t = {
            elem: '#userList',
            url: '../user/findUserList',
            method: 'post',
            toolbar: '#userToolbar',
            page: true,
            height: 360,
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
                    field: 'userName',
                    title: '用户名'
                },
                {
                    field: 'sexStr',
                    title: '性别'
                },
                {
                    field: 'email',
                    title: '邮箱'
                },
                {
                    field: 'mobile',
                    title: '手机号'
                },
                {
                    field: 'creater',
                    title: '创建人'
                },
                {
                    field: 'createTimeStr',
                    title: '创建时间',
                    width: 200
                },
                {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#userRowBar'
                }
            ]],
            done: function (curr) {
                pageCurr = curr;
                $("[data-field='id']").css('display', 'none');
            }
        };

        table.render(t);
        tableInit = table;

        //头工具栏事件
        table.on('toolbar(userList)', function (obj) {
            switch (obj.event) {
                case 'addUser':
                    openUserWin(null, "新增用户");
                    break;
                case 'deleteUserBatch':
                    deleteUserBatch(obj, layer);
                    break;
                case 'importUserBatch':
                    break;
                case 'downUserTemplate':
                    downUserTemplate();
                    break;
            }
        });

        upload.render({
            elem: '#importUserBatch',
            url: '../user/importUserBatch',
            accept: 'file', //普通文件
            multiple: true,
            done: function (res) {
                if (res.returnCode == '0000') {
                    layer.msg("导入成功", {
                        time: 1000
                    }, function () {
                        tableInit.reload('userList');
                    });
                } else {
                    layer.msg(res.returnMessage, {
                        time: 2000
                    });
                }
            }
        });


        //监听工具条
        table.on('tool(userList)', function (obj) {
            let userData = obj.data;
            if (obj.event === 'del') {
                deleteUser(userData);
            } else if (obj.event === 'edit') {
                openUserWin(userData, "编辑");
            }
        });


        //搜索
        form.on("submit(searchSubmit)", function (data) {
            t.where = data.field;
            table.reload('userList', t);
            return false;
        });

        form.on('submit(userSubmit)', function (obj) {
            let roleIds = [];
            $("input:checkbox[name='role']:checked").each(function (i) {
                let roleId = $(this).val();
                roleIds.push(roleId);
            });
            console.info(roleIds);
            obj.field.roleIds = roleIds;
            saveAndUpdateUser(obj);
            return false;
        });

    });
});

//保存更新
function saveAndUpdateUser(userData) {
    $.ajax({
        url: '../user/saveAndUpdate',
        dataType: 'json',
        type: 'post',
        data: userData.field,
        success: function (result) {
            if (result.code == "0000") {
                layer.msg(result.message, {
                    time: 1000
                }, function () {
                    layer.close(userWin); //关闭成功
                    tableInit.reload("userList");
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


let userWin;
// 打开保存，更新窗口
function openUserWin(userData, title) {
    var userId = null;
    var sex = null;
    if (userData != null) {
        sex = userData.sex;
        userId = userData.id;
        $("#id").val(userData.id);
        $("#loginName").val(userData.loginName);
        $("#userName").val(userData.userName);
        $("#age").val(userData.age);
        $("input:radio[name='sex'][value = '" + sex + "']").prop("checked", true);
        $("#email").val(userData.email);
        $("#mobile").val(userData.mobile);
        $("#password").val("******").attr("readOnly", "true").css('background-color', '#DEDEDE');
    } else {
        $("#id").val(""); //新增
        $("#password").val("").attr("readOnly", false);
    }
    let form = layui.form;
    $.ajax({
        url: '../dict/findDictItemListByDictCode',
        dataType: 'json',
        type: 'post',
        data: {
            "dictCode": "1001"
        },
        success: function (data) {
            $.each(data.data, function (index, item) {
                console.info(item);
                var option = null;
                if (sex != null && sex == item.dictItemCode) {
                    option = new Option(item.dictItemName, item.dictItemCode);
                    option.setAttribute("selected", 'true');
                } else {
                    option = new Option(item.dictItemName, item.dictItemCode);
                }
                $('#sexId').append(option); //往下拉菜单里添加元素
                form.render('select'); //这个很重要
            })
        }
    });


    $.ajax({
        type: "POST",
        url: "../user/findUserRoleList",
        data: {
            userId: userId
        },
        async: false,
        success: function (data) {
            //$("#editrolelabelid").html(ret.id);//临时存放id，当提交时再去除赋值给input
            let userRoleChecked = data.userRoleChecked; //该记录已经拥有的记录集合
            let roleListAll = data.roleListAll; //该记录尚未拥有的记录集合
            let checkboxstrs = "";
            $.each(userRoleChecked, function (n, value) { //n从0开始自增+1；value为每次循环的单个对象
                checkboxstrs += '<input type="checkbox" name="role" title="' + value.name + '" value="' + value.id +
                    '"  checked="checked">';
            });
            $.each(roleListAll, function (n, value) {
                checkboxstrs += '<input type="checkbox" name="role" title="' + value.name + '"  value="' + value.id +
                    '" >';
            });
            $("#rolecheckboxlist").empty(); //每次填充前都要清空所有按钮，重新填充
            $("#rolecheckboxlist").append(checkboxstrs);

            layui.form.render(); //更新全部
        }
    });

    userWin = layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['850px'],
        content: $('#setUserWin'),
        end: function () {
            cleanUser();
        }
    })
}


function cleanUser() {
    $("#userName").val("");
    $("#loginName").val("");
    //$("#password").val("");
    $("input:radio[name='sex'][value = '1']").prop("checked", true);
    $('#email').val("");
    $('#mobile').val("");
    $("#password").val("").attr("readOnly", false).css('background-color', '#FFFFFF');
    $('#rolecheckboxlist').html("");
}

//删除用户
function deleteUserBatch(obj, layer) {
    var checkStatus = tableInit.checkStatus('userList');
    var data = checkStatus.data;
    if (data.length > 0) {
        console.log(JSON.stringify(data));
        var deleteParams = {};
        var ids = [];
        for (var i = 0; i < data.length; i++) {
            ids.push(data[i].id);
        }
        deleteParams.ids = ids;
        layer.confirm("你确定要删除这些用户么？", {
            btn: ['确定', '取消']
        }, function () {
            var deleteindex = layer.msg('删除中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            $.ajax({
                type: "POST",
                url: "../user/deleteUserBatch",
                data: deleteParams,
                success: function (res) {
                    layer.close(deleteindex);
                    if (res.code == "0000") {
                        layer.msg("删除成功", {
                            time: 1000
                        }, function () {
                            tableInit.reload('userList');
                        });
                    } else {
                        layer.msg(res.message);
                    }
                }
            });
        })
    } else {
        layer.msg("请选择需要删除的用户", {
            time: 1000
        });
    }
}

//删除数据
function deleteUser(userData) {
    let id = userData.id;
    let userName = userData.userName;
    let confirmWin = layer.confirm("确定要删除用户名为【" + userName + "】吗？", {
        btn: ['确定', '取消'],
        title: "提示"
    }, function () {
        $.post("../user/deleteById", {
            id: id
        }, function (data) {
            if (data.code == "0000") {
                layer.msg(data.message, {
                    time: 3000
                });
                layer.close(confirmWin);
                tableInit.reload("userList");
            } else {
                layer.alert(data.message);
            }
        });
    }, function () {
        console.info("取消删除的操作");
        layer.closeAll();
    })
}

function downUserTemplate() {
    var url = "../user/downUserTemplet";
    var form = $('<form></form>');
    form.attr('action', url);
    form.attr('method', 'post');
    $(document.body).append(form);
    form.submit();
    form.remove();
}