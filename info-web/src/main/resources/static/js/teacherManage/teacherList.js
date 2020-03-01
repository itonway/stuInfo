var tableInit;
let pageCurr;
var formInit;
var assignStuWin;
$(function () {
    layui.use(['layer', 'form', 'table', 'upload', 'util', 'laydate'], function () {
        var $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            table = layui.table,
            upload = layui.upload,
            laydate = layui.laydate,
            t;
        t = {
            elem: '#teacherList',
            url: '../teacher/findTeacherList',
            method: 'post',
            toolbar: '#teacherToolbar',
            cellMinWidth: 120,
            height: 360,
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
                    field: 'teacherNo',
                    title: '教师编号'
                },
                {
                    field: 'teacherName',
                    title: '教师名称'
                },
                {
                    field: 'teacherCard',
                    title: '身份证号'
                },
                {
                    field: 'teacherSexStr',
                    title: '性别'
                },
                {
                    field: 'teacherBirthDateStr',
                    title: '出生年月'
                },
                {
                    field: 'teacherMobile',
                    title: '手机号'
                },
                {
                    field: 'teacherEmail',
                    title: '邮箱'
                },
                {
                    field: 'teacherAddress',
                    title: '家庭住址'
                },
                {
                    field: 'startJobDateStr',
                    title: '入职日期'
                },
                {
                    field: 'endJobDateStr',
                    title: '离职日期'
                },
                {
                    field: 'teacherRemark',
                    title: '备注'
                },
                {
                    field: 'creater',
                    title: '创建人'
                },
                {
                    field: 'createTimeStr',
                    title: '创建时间'
                },
                {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#teacherRowBar'
                }
            ]],
            done: function (curr) {
                pageCurr = curr;
                $("[data-field='id']").css('display', 'none');
            }
        };

        table.render(t);
        tableInit = table;
        formInit = form;

        initParam(laydate, form);

        //头工具栏事件
        table.on('toolbar(teacherList)', function (obj) {
            switch (obj.event) {
                case 'addTeacher':
                    openTeacherWin(null, "新增教师");
                    break;
                case 'deleteTeacherBatch':
                    deleteTeacherBatch(obj, layer);
                    break;
                case 'importTeacherBatch':
                    break;
                case 'downTeacherTemplate':
                    downTeacherTemplate();
                    break;
                case 'exportTeacherBatch':
                    exportTeacherBatch();
                    break;

            }
        });


        //导入班级信息
        upload.render({
            elem: '#importTeacherBatchId',
            url: '../teacher/importTeacherBatch',
            accept: 'file', //普通文件
            multiple: true,
            exts: 'xls|xlsx',
            done: function (res) {
                if (res.returnCode == '0000') {
                    layer.msg("导入成功", {
                        time: 1000
                    }, function () {
                        table.reload('teacherList');
                    });
                } else {
                    layer.msg(res.returnMessage, {
                        time: 2000
                    });
                }
            }
        });

        //监听工具条
        table.on('tool(teacherList)', function (obj) {
            let teacherData = obj.data;
            if (obj.event === 'del') {
                deleteTeacher(teacherData);
            } else if (obj.event === 'edit') {
                openTeacherWin(teacherData, "编辑教师");
            }
        });


        //新增，编辑提交
        form.on('submit(teacherSubmit)', function (obj) {
            console.log("接收到的参数：" + obj.field);
            saveAndUpdateTeacher(obj);
            return false;
        });

        //搜索 提交
        form.on("submit(searchSubmitTeacher)", function (data) {
            t.where = data.field;
            table.reload('teacherList', t);
            return false;
        });

    })


});

//初始化参数
function initParam(laydate, form) {
    // let myNewDate = new Date();
    // myNewDate.setDate(myNewDate.getDate() - 7);//30天前的日期
    laydate.render({
        elem: '#teacherCreateTime'
    });

    laydate.render({
        elem: '#teacherBirthDate'
    });

    laydate.render({
        elem: '#teacherStartJobDate'
    });

    laydate.render({
        elem: '#teacherEndJobDate'
    });

    //初始化字典 组件
    $.ajax({
        url: '../dict/findDictItemListByDictCode',
        type: 'post',
        data: {
            "dictCode": "1001"
        },
        success: function (data) {
            $.each(data.data, function (index, item) {
                $('#sexSerachTeacher').append(new Option(item.dictItemName, item.dictItemCode));
            });
            form.render();
        }
    })


}


//保存更新
function saveAndUpdateTeacher(teacherData) {
    $.ajax({
        url: '../teacher/saveAndUpdateTeacher',
        dataType: 'json',
        type: 'post',
        data: teacherData.field,
        success: function (result) {
            if (result.code == "0000") {
                layer.msg(result.message, {
                    time: 1000
                }, function () {
                    layer.close(teacherWin); //关闭成功
                    tableInit.reload("teacherList");
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


let teacherWin;

// 打开保存，更新窗口
function openTeacherWin(teacherData, title) {
    var teacherId = null;
    if (teacherData != null) {
        teacherId = teacherData.id;
        teacherSex = teacherData.teacherSex;
        $("#id").val(teacherId);
        $("#teacherNo").val(teacherData.teacherNo);
        $("#teacherName").val(teacherData.teacherName);
        $("#teacherPassword").val(teacherData.teacherPassword);
        $("#teacherBirthDate").val(teacherData.teacherBirthDate);
        $("input:radio[name='teacherSex'][value = '" + teacherSex + "']").prop("checked", true);
        $("#teacherCard").val(teacherData.teacherCard);
        $("#teacherEmail").val(teacherData.teacherEmail);
        $("#teacherMobile").val(teacherData.teacherMobile);
        $("#teacherStartJobDate").val(teacherData.startJobDate);
        $("#teacherEndJobDate").val(teacherData.endJobDate);
        $("#teacherAddress").val(teacherData.teacherAddress);
        $("#teacherRemark").val(teacherData.teacherRemark);

        formInit.render();  // 不然 性别显示错误
    } else {
        $("#id").val(""); //新增
    }
    teacherWin = layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['700px'],
        content: $('#setTeacherWin'),
        end: function () {
            cleanTeacher();
        }
    })
}

//清空数据
function cleanTeacher() {
    $("#id").val("");
    $("#teacherNo").val("");
    $("#teacherName").val("");
    $("#teacherPassword").val("");
    $("#teacherAge").val("");
    $("#teacherBirthDate").val("");
    $("input:radio[name='teacherSex'][value = '1']").prop("checked", true);
    $("#teacherCard").val("");
    $("#teacherEmail").val("");
    $("#teacherMobile").val("");
    $("#teacherStartJobDate").val("");
    $("#teacherEndJobDate").val("");
    $("#teacherAddress").val("");
    $("#teacherRemark").val("");

    formInit.render();  // 不然 性别显示错误
}

//删除数据
function deleteTeacher(teacherData) {
    let id = teacherData.id;
    let teacherName = teacherData.teacherName;
    let confirmWin = layer.confirm("确定要删除教师名称为【" + teacherName + "】吗？", {
        btn: ['确定', '取消'],
        title: "提示"
    }, function () {
        $.post("../teacher/deleteById", {
            id: id
        }, function (data) {
            if (data.code == "0000") {
                layer.msg(data.message, {
                    time: 3000
                });
                layer.close(confirmWin);
                tableInit.reload("teacherList");
            } else {
                layer.alert(data.message);
            }
        });
    }, function () {
        console.info("取消删除的操作");
        layer.closeAll();
    })
}

//批量删除班级
function deleteTeacherBatch(obj, layer) {
    var checkStatus = tableInit.checkStatus(obj.config.id);
    var data = checkStatus.data;
    if (data.length > 0) {
        var deleteParams = {};
        var ids = [];
        for (var i = 0; i < data.length; i++) {
            ids.push(data[i].id);
        }
        deleteParams.ids = ids;
        layer.confirm("你确定要删除这些教师么？", {
            btn: ['确定', '取消']
        }, function () {
            var deleteindex = layer.msg('删除中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            $.ajax({
                type: "POST",
                url: "../teacher/deleteTeacherBatch",
                data: deleteParams,
                success: function (res) {
                    layer.close(deleteindex);
                    if (res.code == "0000") {
                        layer.msg("删除成功", {
                            time: 1000
                        }, function () {
                            tableInit.reload('teacherList');
                        });
                    } else {
                        layer.msg(res.message);
                    }
                }
            });
        })
    } else {
        layer.msg("请选择需要删除的教师", {
            time: 1000
        });
    }
}


//导出班级
function exportTeacherBatch() {
    let params = {};
    params.teacherName = $(".teacherName").val();
    params.teacherSex = $("#sexSerachTeacher").val();
    params.startJobDate = $("#teacherCreateTime").val();
    var url = "../teacher/exportTeacherBatch";
    var form = $('<form></form>');
    form.attr('action', url);
    form.attr('method', 'post');
    for (var item in params) {
        var formInput = $('<input name="' + item + '" type="hidden" value="' + params[item] + '" />');
        form.append(formInput);
    }
    $(document.body).append(form);
    form.submit();
    form.remove();
}

//下载模板
function downTeacherTemplate() {
    var url = "../teacher/downTeacherTemplet";
    var form = $('<form></form>');
    form.attr('action', url);
    form.attr('method', 'post');
    $(document.body).append(form);
    form.submit();
    form.remove();
}


// 分配学生信息窗口
function openAssignStuWin(id, title) {
    assignStuWin = layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['700px'],
        content: $('#assignStuWin'),
        end: function () {
            assignStuWin.close();
        }
    })
}
