var tableInit;
let pageCurr;
var formInit;
$(function () {
    layui.use(['layer', 'form', 'table', 'upload', 'laydate'], function () {
        var layer = layui.layer,
            $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            upload = layui.upload,
            laydate = layui.laydate,
            t;
        t = {
            elem: '#stuList',
            url: '../stu/findStuList',
            method: 'post',
            toolbar: '#stuToolbar',
            page: true,
            cellMinWidth: 120,
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
                    field: 'stuNo',
                    title: '学号'
                },
                {
                    field: 'stuName',
                    title: '姓名'
                },
                {
                    field: 'classNo',
                    title: '班级编号'
                },
                {
                    field: 'className',
                    title: '班级名称'
                },
                {
                    field: 'stuAge',
                    title: '年龄'
                },
                {
                    field: 'stuSexStr',
                    title: '性别'
                },
                {
                    field: 'stuBirthDateStr',
                    title: '出生年月'
                },
                {
                    field: 'stuCard',
                    title: '身份证号'
                },
                {
                    field: 'stuMobile',
                    title: '手机号'
                },
                {
                    field: 'stuEmail',
                    title: '邮箱'
                },
                {
                    field: 'stuStartDateStr',
                    title: '入学日期'
                },
                {
                    field: 'stuEndDateStr',
                    title: '毕业日期'
                },
                {
                    field: 'stuAddress',
                    title: '家庭住址'
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
                    field: 'stuRemark',
                    title: '备注'
                },
                {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#stuRowBar'
                }
            ]],
            height: 360,
            done: function (curr) {
                pageCurr = curr;
                $("[data-field='id']").css('display', 'none');
            }
        };

        table.render(t);
        tableInit = table;
        formInit = form;
        //初始化参数
        initParam(laydate, form);


        //头工具栏事件
        table.on('toolbar(stuList)', function (obj) {
            switch (obj.event) {
                case 'addStu':
                    openStuWin(null, "新增学生");
                    break;
                case 'deleteStuBatch':
                    deleteStuBatch(obj, layer);
                    break;
                case 'importStuBatch':
                    console.log("批量导入学生信息");
                    break;
                case 'downStuTemplate':
                    downStuTemplate();
                    break;
                case 'exportStuBatch':
                    exportStuBatch($);
                    break;
            }
        });



        upload.render({
            elem: '#importStuBatchId',
            url: '../stu/importStuBatch',
            accept: 'file', //普通文件
            multiple: true,
            exts: 'xls|xlsx',
            done: function (res) {
                if (res.returnCode == '0000') {
                    layer.msg("导入成功", {
                        time: 1000
                    }, function () {
                        table.reload('stuList');
                    });
                } else {
                    layer.msg(res.returnMessage, {
                        time: 2000
                    });
                }
            }
        });

        //监听工具条
        table.on('tool(stuList)', function (obj) {
            let stuData = obj.data;
            if (obj.event === 'del') {
                deleteStu(stuData);
            } else if (obj.event === 'edit') {
                openStuWin(stuData, "编辑");
            }
        });


        //新增，编辑提交
        form.on('submit(stuSubmit)', function (obj) {
            console.log("接收到的参数：" + obj.field);
            saveAndUpdateStu(obj);
            return false;
        });

        //搜索 提交
        form.on("submit(searchSubmitStu)", function (data) {
            t.where = data.field;
            table.reload('stuList', t);
            return false;
        });
    })

});


//初始化参数
function initParam(laydate, form) {
    //初始化时间组件
    // let myNewDate = new Date();
    // myNewDate.setDate(myNewDate.getDate() - 7);//30天前的日期
    laydate.render({
        elem: '#stuStartDateSerach'
    });

    laydate.render({
        elem: '#stuBirthDate'
    });

    laydate.render({
        elem: '#stuEndDateAdd'
    });

    laydate.render({
        elem: '#stuStartDateAdd'
        //type: 'datetime'  时分秒
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
                $('#sexSerach').append(new Option(item.dictItemName, item.dictItemCode));
            });
            form.render();
        }
    })


    //初始化 班级信息
    $.ajax({
        url: '../class/findAllClassList',
        type: 'post',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#classNoSerach').append(new Option(item.className, item.id));
                $('#classId').append(new Option(item.className, item.id));
            });
            form.render();
        }
    })
}


//保存更新
function saveAndUpdateStu(stuData) {
    $.ajax({
        url: '../stu/saveAndUpdateStu',
        dataType: 'json',
        type: 'post',
        data: stuData.field,
        success: function (result) {
            if (result.code == "0000") {
                layer.msg(result.message, {
                    time: 1000
                }, function () {
                    layer.close(stuWin); //关闭成功
                    tableInit.reload("stuList");
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


let stuWin;
// 打开保存，更新窗口
function openStuWin(stuData, title) {
    if (stuData != null) {
        stuSex = stuData.stuSex;
        $("#id").val(stuData.id);
        $("#stuNo").val(stuData.stuNo);
        $("#stuName").val(stuData.stuName);
        $("#stuAge").val(stuData.stuAge);
        $("input:radio[name='stuSex'][value = '" + stuSex + "']").prop("checked", true);
        $("#stuBirthDate").val(stuData.stuBirthDate);
        $("#stuCard").val(stuData.stuCard);
        $("#stuEmail").val(stuData.stuEmail);
        $("#stuMobile").val(stuData.stuMobile);
        $("#stuStartDateAdd").val(stuData.stuStartDate);
        $("#stuEndDateAdd").val(stuData.stuEndDate);
        $("#classId").val(stuData.classId);
        $("#stuAddress").val(stuData.stuAddress);
        $("#stuRemark").val(stuData.stuRemark);

        formInit.render();  // 不然 性别显示错误
    } else {
        $("#id").val(""); //新增
    }

    stuWin = layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['850px'],
        content: $('#setStuWin'),
        end: function () {
            cleanStu();
        }
    })
}


function cleanStu() {
    $("#stuNo").val("");
    $("#stuName").val("");
    $("#stuAge").val("");
    $("input:radio[name='stuSex'][value = '1']").prop("checked", true);
    $("#stuBirthDate").val("");
    $("#stuCard").val("");
    $("#stuEmail").val("");
    $("#stuMobile").val("");
    $("#stuStartDateAdd").val("");
    $("#stuEndDateAdd").val("");
    $("#classId").val("");
    $("#stuAddress").val("");
    $("#stuRemark").val("");

    formInit.render();  // 不然 性别显示错误
}


function exportStuBatch($) {
    let params = {};
    params.stuName = $(".stuName").val();
    params.stuSex = $("#sexSerach").val();
    params.stuStartDate = $("#stuStartDateSerach").val();
    params.classId = $("#classNoSerach").val();
    var url = "../stu/exportStuBatch";
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


//删除数据
function deleteStu(stuData) {
    let id = stuData.id;
    let stuName = stuData.stuName;
    let confirmWin = layer.confirm("确定要删除学生姓名为【" + stuName + "】吗？", {
        btn: ['确定', '取消'],
        title: "提示"
    }, function () {
        $.post("../stu/deleteById", {
            id: id
        }, function (data) {
            if (data.code == "0000") {
                layer.msg(data.message, {
                    time: 3000
                });
                layer.close(confirmWin);
                tableInit.reload("stuList");
            } else {
                layer.alert(data.message);
            }
        });
    }, function () {
        console.info("取消删除的操作");
        layer.closeAll();
    })
}


function deleteStuBatch(obj, layer) {
    var checkStatus = tableInit.checkStatus(obj.config.id);
    var data = checkStatus.data;
    if (data.length > 0) {
        var deleteParams = {};
        var ids = [];
        for (var i = 0; i < data.length; i++) {
            ids.push(data[i].id);
        }
        deleteParams.ids = ids;
        layer.confirm("确定要删除这些学生信息么？", {
            btn: ['确定', '取消']
        }, function () {
            var deleteindex = layer.msg('删除中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            $.ajax({
                type: "POST",
                url: "../stu/deleteStuBatch",
                data: deleteParams,
                success: function (res) {
                    layer.close(deleteindex);
                    if (res.code == "0000") {
                        layer.msg("删除成功", {
                            time: 1000
                        }, function () {
                            tableInit.reload('stuList');
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


function downStuTemplate() {
    var url = "../stu/downStuTemplet";
    var form = $('<form></form>');
    form.attr('action', url);
    form.attr('method', 'post');
    $(document.body).append(form);
    form.submit();
    form.remove();
}