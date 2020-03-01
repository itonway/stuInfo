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
            elem: '#scoreList',
            url: '../score/findScoreList',
            method: 'post',
            toolbar: '#scoreToolbar',
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
                    title: '学生姓名'
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
                    field: 'courseName',
                    title: '课程名称'
                },
                {
                    field: 'score',
                    title: '分数'
                },
                {
                    field: 'scoreRemark',
                    title: '备注'
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
                    toolbar: '#scoreRowBar',
                    width: 150
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
        initParam(form);

        //头工具栏事件
        table.on('toolbar(scoreList)', function (obj) {
            switch (obj.event) {
                case 'addScore':
                    openScoreWin(null, "新增分数");
                    break;
                case 'deleteScoreBatch':
                    deleteScoreBatch(obj, layer);
                    break;
                case 'importScoreBatch':
                    console.log("批量导入学生成绩信息");
                    break;
                case 'downScoreTemplate':
                    downScoreTemplate();
                    break;
                case 'exportScoreBatch':
                    exportScoreBatch($);
                    break;
            }
        });


        upload.render({
            elem: '#importScoreBatchId',
            url: '../score/importScoreBatch',
            accept: 'file', //普通文件
            multiple: true,
            exts: 'xls|xlsx',
            done: function (res) {
                if (res.returnCode == '0000') {
                    layer.msg("导入成功", {
                        time: 1000
                    }, function () {
                        table.reload('scoreList');
                    });
                } else {
                    layer.msg(res.returnMessage, {
                        time: 2000
                    });
                }
            }
        });


        //监听工具条
        table.on('tool(scoreList)', function (obj) {
            let scoreData = obj.data;
            if (obj.event === 'del') {
                deleteScore(scoreData);
            } else if (obj.event === 'edit') {
                openScoreWin(scoreData, "编辑");
            }
        });


        //新增，编辑提交
        form.on('submit(scoreSubmit)', function (obj) {
            console.log("接收到的参数：" + obj.field);
            saveAndUpdateScore(obj);
            return false;
        });

        //搜索 提交
        form.on("submit(searchSubmitScore)", function (data) {
            t.where = data.field;
            table.reload('scoreList', t);
            return false;
        });
    })

});


//初始化参数
function initParam(form) {
    //初始化 班级信息
    $.ajax({
        url: '../class/findAllClassList',
        type: 'post',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#scoreClassIdSerach').append(new Option(item.className, item.id));
            });
            form.render();
        }
    });

    //初始化学生下拉框
    $.ajax({
        url: '../stu/findAllStuList',
        type: 'post',
        success: function (result) {
            $.each(result, function (index, item) {
                $('#scoreStuIdSerach').append(new Option(item.stuName, item.id));
                $('#scoreStuId').append(new Option(item.stuName, item.id));
            });
            form.render();
        }
    });

    //初始化课程下拉框
    $.ajax({
        url: '../course/findAllCourseList',
        type: 'post',
        success: function (result) {
            $.each(result, function (index, item) {
                $('#scoreCourseIdSearch').append(new Option(item.courseName, item.id));
                $('#scoreCourseId').append(new Option(item.courseName, item.id));
            });
            form.render();
        }
    })
}


//保存更新
function saveAndUpdateScore(scoreData) {
    $.ajax({
        url: '../score/saveAndUpdateScore',
        dataType: 'json',
        type: 'post',
        data: scoreData.field,
        success: function (result) {
            if (result.code == "0000") {
                layer.msg(result.message, {
                    time: 1000
                }, function () {
                    layer.close(scoreWin); //关闭成功
                    tableInit.reload("scoreList");
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


let scoreWin;

// 打开保存，更新窗口
function openScoreWin(scoreData, title) {
    if (scoreData != null) {
        $("#id").val(scoreData.id);
        $("#scoreStuId").val(scoreData.stuId);
        $("#scoreCourseId").val(scoreData.courseId);
        $("#score").val(scoreData.score);
        $("#scoreRemark").val(scoreData.scoreRemark);

        formInit.render();  // 不然 性别显示错误
    } else {
        $("#id").val(""); //新增
    }

    scoreWin = layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['850px'],
        content: $('#setScoreWin'),
        end: function () {
            cleanScore();
        }
    })
}


function cleanScore() {
    $("#scoreStuId").val("");
    $("#scoreCourseId").val("");
    $("#score").val("");
    $("#scoreRemark").val("");
    formInit.render();  // 不然 性别显示错误
}

//导出成绩
function exportScoreBatch($) {
    let params = {};
    params.stuId = $("#scoreStuIdSerach").val();
    params.classId = $("#scoreClassIdSerach").val();
    params.courseId = $("#scoreCourseIdSearch").val();
    var url = "../score/exportScoreBatch";
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
function deleteScore(scoreData) {
    let id = scoreData.id;
    let stuName = scoreData.stuName;
    let confirmWin = layer.confirm("确定要删除学生姓名为【" + stuName + "】的成绩吗？", {
        btn: ['确定', '取消'],
        title: "提示"
    }, function () {
        $.post("../score/deleteById", {
            id: id
        }, function (data) {
            if (data.code == "0000") {
                layer.msg(data.message, {
                    time: 3000
                });
                layer.close(confirmWin);
                tableInit.reload("scoreList");
            } else {
                layer.alert(data.message);
            }
        });
    }, function () {
        console.info("取消删除的操作");
        layer.closeAll();
    })
}


function deleteScoreBatch(obj, layer) {
    var checkStatus = tableInit.checkStatus(obj.config.id);
    var data = checkStatus.data;
    if (data.length > 0) {
        var deleteParams = {};
        var ids = [];
        for (var i = 0; i < data.length; i++) {
            ids.push(data[i].id);
        }
        deleteParams.ids = ids;
        layer.confirm("确定要删除这些成绩信息么？", {
            btn: ['确定', '取消']
        }, function () {
            var deleteindex = layer.msg('删除中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            $.ajax({
                type: "POST",
                url: "../score/deleteScoreBatch",
                data: deleteParams,
                success: function (res) {
                    layer.close(deleteindex);
                    if (res.code == "0000") {
                        layer.msg("删除成功", {
                            time: 1000
                        }, function () {
                            tableInit.reload('scoreList');
                        });
                    } else {
                        layer.msg(res.message);
                    }
                }
            });
        })
    } else {
        layer.msg("请选择需要删除的成绩信息", {
            time: 1000
        });
    }
}


function downScoreTemplate() {
    var url = "../score/downScoreTemplet";
    var form = $('<form></form>');
    form.attr('action', url);
    form.attr('method', 'post');
    $(document.body).append(form);
    form.submit();
    form.remove();
}