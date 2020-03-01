var tableInit;
let pageCurr;
var formInit;
var assignStuWin;
$(function () {
    layui.use(['layer', 'form', 'table', 'upload', 'util', 'transfer'], function () {
        var $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            table = layui.table,
            upload = layui.upload,
            util = layui.util,
            transfer = layui.transfer,
            t;
        t = {
            elem: '#classList',
            url: '../class/findClassList',
            method: 'post',
            toolbar: '#classToolbar',
            cellMinWidth: 120,
            page: true,
            height: 380,
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
                    field: 'classNo',
                    title: '班级编号'
                },
                {
                    field: 'className',
                    title: '班级名称'
                },
                {
                    field: 'classRemark',
                    title: '备注'
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
                    toolbar: '#classRowBar'
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


        //头工具栏事件
        table.on('toolbar(classList)', function (obj) {
            switch (obj.event) {
                case 'addClass':
                    openClassWin(null, "新增班级");
                    break;
                case 'deleteClassBatch':
                    deleteClassBatch(obj, layer);
                    break;
                case 'importClassBatch':
                    console.log("批量导入班级信息");
                    break;
                case 'downClassTemplate':
                    downClassTemplate();
                    break;
                case 'exportClassBatch':
                    exportClassBatch();
                    break;
                case 'assignStuBatch':
                    var checkStatus = table.checkStatus(obj.config.id);
                    var chooseData = checkStatus.data;
                    if (chooseData.length == 1) {
                        var id = chooseData[0].id;
                        var classNo = chooseData[0].classNo;
                        openAssignStuWin(id, "分配学生");
                        var stuList = initStuItem(id);
                        transfer.render({
                            elem: '#assignStuWinTransfer',
                            data: stuList,
                            id: 'transferStuId',
                            title: ['待选择的学生', '已选择的学生'],
                            showSearch: true,
                            width: 180,//定义宽度
                            height: 310 //定义高度
                        });


                        //保存分配的学生
                        util.event('lay-demoTransferActive', {
                            getSelectStuData: function () {
                                var saveAssignedStuParams = {};
                                var getData = transfer.getData('transferStuId'); //获取右侧数据
                                saveAssignedStuParams.classId = id;
                                saveAssignedStuParams.classNo = classNo;
                                saveAssignedStuParams.infoStuListStr = JSON.stringify(getData);
                                $.ajax({
                                    type: "POST",
                                    url: "../class/saveAssignedStu",
                                    data: saveAssignedStuParams,
                                    success: function (res) {
                                        if (res.code == "0000") {
                                            layer.msg("保存成功", {
                                                time: 1000
                                            }, function () {
                                                layer.close(assignStuWin);
                                                tableInit.reload('classList');
                                            });
                                        } else {
                                            layer.msg(res.message);
                                        }
                                    }
                                });


                            },
                            getSelectStuDataCancel: function () {
                                layer.close(assignStuWin);
                            }
                        });
                    } else {
                        layer.msg("请选择一个需要分配学生的班级", {
                            time: 1500
                        });
                    }
                    break;
            }
        });


        //导入班级信息
        upload.render({
            elem: '#importClassBatchId',
            url: '../class/importClassBatch',
            accept: 'file', //普通文件
            multiple: true,
            exts: 'xls|xlsx',
            done: function (res) {
                if (res.returnCode == '0000') {
                    layer.msg("导入成功", {
                        time: 1000
                    }, function () {
                        table.reload('classList');
                    });
                } else {
                    layer.msg(res.returnMessage, {
                        time: 2000
                    });
                }
            }
        });

        //监听工具条
        table.on('tool(classList)', function (obj) {
            let classData = obj.data;
            if (obj.event === 'del') {
                deleteClass(classData);
            } else if (obj.event === 'edit') {
                openClassWin(classData, "编辑班级");
            }
        });


        //新增，编辑提交
        form.on('submit(classSubmit)', function (obj) {
            console.log("接收到的参数：" + obj.field);
            saveAndUpdateClass(obj);
            return false;
        });

        //搜索 提交
        form.on("submit(searchSubmitClass)", function (data) {
            t.where = data.field;
            table.reload('classList', t);
            return false;
        });


    })


});


function initStuItem(id) {
    var stuList = [];
    var initStuItemParams = {};
    initStuItemParams.assignClassId = id;
    $.ajax({
        type: "POST",
        url: "../class/findStuListAssignClass",
        data: initStuItemParams,
        async: false,
        success: function (res) {
            stuList = res;
        }
    });
    return stuList;
}



//保存更新
function saveAndUpdateClass(classData) {
    $.ajax({
        url: '../class/saveAndUpdateClass',
        dataType: 'json',
        type: 'post',
        data: classData.field,
        success: function (result) {
            if (result.code == "0000") {
                layer.msg(result.message, {
                    time: 1000
                }, function () {
                    layer.close(classWin); //关闭成功
                    tableInit.reload("classList");
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


let classWin;
// 打开保存，更新窗口
function openClassWin(classData, title) {
    var classId = null;
    if (classData != null) {
        classId = classData.id;
        $("#id").val(classId);
        $("#classNo").val(classData.classNo);
        $("#className").val(classData.className);
        $("#classRemark").val(classData.classRemark);

        formInit.render();  // 不然 性别显示错误
    } else {
        $("#id").val(""); //新增
    }
    classWin = layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['700px'],
        content: $('#setClassWin'),
        end: function () {
            cleanClass();
        }
    })
}

//清空数据 
function cleanClass() {
    $("#classNo").val("");
    $("#className").val("");
    $("#classRemark").val("");
}

//删除数据
function deleteClass(classData) {
    let id = classData.id;
    let className = classData.className;
    let confirmWin = layer.confirm("删除班级同时删除该班级的学生和成绩，确定要删除班级名称为【" + className + "】吗？", {
        btn: ['确定', '取消'],
        title: "提示"
    }, function () {
        $.post("../class/deleteById", {
            id: id
        }, function (data) {
            if (data.code == "0000") {
                layer.msg(data.message, {
                    time: 3000
                });
                layer.close(confirmWin);
                tableInit.reload("classList");
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
function deleteClassBatch(obj, layer) {
    var checkStatus = tableInit.checkStatus(obj.config.id);
    var data = checkStatus.data;
    if (data.length > 0) {
        var deleteParams = {};
        var ids = [];
        for (var i = 0; i < data.length; i++) {
            ids.push(data[i].id);
        }
        deleteParams.ids = ids;
        layer.confirm("删除班级同时删除该班级的学生和成绩，你确定要删除么？", {
            btn: ['确定', '取消']
        }, function () {
            var deleteindex = layer.msg('删除中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            $.ajax({
                type: "POST",
                url: "../class/deleteClassBatch",
                data: deleteParams,
                success: function (res) {
                    layer.close(deleteindex);
                    if (res.code == "0000") {
                        layer.msg("删除成功", {
                            time: 1000
                        }, function () {
                            tableInit.reload('classList');
                        });
                    } else {
                        layer.msg(res.message);
                    }
                }
            });
        })
    } else {
        layer.msg("请选择需要删除的班级", {
            time: 1000
        });
    }
}


//导出班级
function exportClassBatch() {
    let params = {};
    params.classNo = $(".classNo").val();
    params.className = $(".className").val();
    var url = "../class/exportClassBatch";
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
function downClassTemplate() {
    var url = "../class/downClassTemplet";
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
        area: ['600px'],
        content: $('#assignStuWin'),
        end: function () {
            //assignStuWin.close();
        }
    })
}
