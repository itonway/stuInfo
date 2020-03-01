var tableInit;
let pageCurr;
var formInit;
$(function () {
    layui.use(['layer', 'form', 'table', 'upload'], function () {
        var $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            table = layui.table,
            upload = layui.upload,
            t;
        t = {
            elem: '#courseList',
            url: '../course/findCourseList',
            method: 'post',
            toolbar: '#courseToolbar',
            cellMinWidth: 120,
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
                    field: 'courseNo',
                    title: '课程编号'
                },
                {
                    field: 'courseName',
                    title: '课程名称'
                },
                {
                    field: 'teacherName',
                    title: '任课老师'
                },
                {
                    field: 'courseRemark',
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
                    toolbar: '#courseRowBar'
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

        initParams();

        //头工具栏事件
        table.on('toolbar(courseList)', function (obj) {
            switch (obj.event) {
                case 'addCourse':
                    openCourseWin(null, "新增课程");
                    break;
                case 'deleteCourseBatch':
                    deleteCourseBatch(obj, layer);
                    break;
                case 'importCourseBatch':
                    break;
                case 'downCourseTemplate':
                    downCourseTemplate();
                    break;
            }
        });


        //导入课程信息
        upload.render({
            elem: '#importCourseBatchId',
            url: '../course/importCourseBatch',
            accept: 'file', //普通文件
            multiple: true,
            exts: 'xls|xlsx',
            done: function (res) {
                if (res.returnCode == '0000') {
                    layer.msg("导入成功", {
                        time: 1000
                    }, function () {
                        table.reload('courseList');
                    });
                } else {
                    layer.msg(res.returnMessage, {
                        time: 2000
                    });
                }
            }
        });

        //监听工具条
        table.on('tool(courseList)', function (obj) {
            let courseData = obj.data;
            if (obj.event === 'del') {
                deleteCourse(courseData);
            } else if (obj.event === 'edit') {
                openCourseWin(courseData, "编辑课程");
            }
        });


        //新增，编辑提交
        form.on('submit(courseSubmit)', function (obj) {
            saveAndUpdateCourse(obj);
            return false;
        });

        //搜索 提交
        form.on("submit(searchSubmitCourse)", function (data) {
            t.where = data.field;
            table.reload('courseList', t);
            return false;
        });




    })


});


//保存更新
function saveAndUpdateCourse(courseData) {
    $.ajax({
        url: '../course/saveAndUpdateCourse',
        dataType: 'json',
        type: 'post',
        data: courseData.field,
        success: function (result) {
            if (result.code == "0000") {
                layer.msg(result.message, {
                    time: 1000
                }, function () {
                    layer.close(courseWin); //关闭成功
                    tableInit.reload("courseList");
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


let courseWin;

function initParams() {
    //初始化教师信息
    $.ajax({
        url: '../teacher/findAllTeacherList',
        type: 'post',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#courseTeacherId').append(new Option(item.teacherName, item.id));
            });
            formInit.render();
        }
    });
}

// 打开保存，更新窗口
function openCourseWin(courseData, title) {
    var courseId = null;
    if (courseData != null) {
        courseId = courseData.id;
        $("#id").val(courseId);
        $("#courseNo").val(courseData.courseNo);
        $("#courseName").val(courseData.courseName);
        $("#courseTeacherId").val(courseData.teacherId);
        $("#courseRemark").val(courseData.courseRemark);
        formInit.render();  // 不然 性别显示错误
    } else {
        $("#id").val(""); //新增
        $("#courseTeacherId").val("");
    }
    courseWin = layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['700px'],
        content: $('#setCourseWin'),
        end: function () {
            cleanCourse();
        }
    })
}

//清空数据 
function cleanCourse() {
    $("#courseNo").val("");
    $("#courseName").val("");
    $("#courseTeacherId").val("");
    $("#courseRemark").val("");
}

//删除数据
function deleteCourse(courseData) {
    let id = courseData.id;
    let courseName = courseData.courseName;
    let confirmWin = layer.confirm("确定要删除课程名称为【" + courseName + "】吗？", {
        btn: ['确定', '取消'],
        title: "提示"
    }, function () {
        $.post("../course/deleteById", {
            id: id
        }, function (data) {
            if (data.code == "0000") {
                layer.msg(data.message, {
                    time: 3000
                });
                layer.close(confirmWin);
                tableInit.reload("courseList");
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
function deleteCourseBatch(obj, layer) {
    var checkStatus = tableInit.checkStatus(obj.config.id);
    var data = checkStatus.data;
    if (data.length > 0) {
        var deleteParams = {};
        var ids = [];
        for (var i = 0; i < data.length; i++) {
            ids.push(data[i].id);
        }
        deleteParams.ids = ids;
        layer.confirm("你确定要删除这些课程么？", {
            btn: ['确定', '取消']
        }, function () {
            var deleteindex = layer.msg('删除中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            $.ajax({
                type: "POST",
                url: "../course/deleteCourseBatch",
                data: deleteParams,
                success: function (res) {
                    layer.close(deleteindex);
                    if (res.code == "0000") {
                        layer.msg("删除成功", {
                            time: 1000
                        }, function () {
                            tableInit.reload('courseList');
                        });
                    } else {
                        layer.msg(res.message);
                    }
                }
            });
        })
    } else {
        layer.msg("请选择需要删除的课程", {
            time: 1000
        });
    }
}


//下载模板
function downCourseTemplate() {
    var url = "../course/downCourseTemplet";
    var form = $('<form></form>');
    form.attr('action', url);
    form.attr('method', 'post');
    $(document.body).append(form);
    form.submit();
    form.remove();
}

