package com.info.manage.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.github.pagehelper.PageInfo;
import com.info.manage.entity.InfoCourse;
import com.info.manage.entity.User;
import com.info.manage.filter.ExcelListener;
import com.info.manage.form.CourseImport;
import com.info.manage.service.ICourseService;
import com.info.manage.service.IDictItemService;
import com.info.manage.service.IStuService;
import com.info.manage.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author xxy
 * @Description //TODO  课程管理
 * @Date 2019/7/5 11:17
 **/
@RestController
@RequestMapping(value = "course")
@Validated
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger ( CourseController.class );

    @Autowired
    ICourseService courseService;
    @Autowired
    IDictItemService dictItemService;
    @Autowired
    IStuService stuService;

    /**
     * @return org.springframework.web.servlet.ModelAndView
     * @Author xxy
     * @Description //TODO   跳转到班级页面
     * @Date 2019/7/11 11:08
     * @Param [modelAndView]
     **/
    @RequestMapping(value = "/jumpCourseList")
    public ModelAndView jumpCourseList(ModelAndView modelAndView) {
        modelAndView.setViewName ( "courseManage/courseList" );
        return modelAndView;
    }

    /**
     * @return com.info.manage.util.PageResult<com.info.manage.entity.InfoCourse>
     * @Author xxy
     * @Description //TODO 查询课程列表
     * @Date 2019/7/15 10:09
     * @Param [page, limit, infoCourse]
     **/
    @RequestMapping(value = "findCourseList", method = RequestMethod.POST)
    public PageResult<InfoCourse> findCourseList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            InfoCourse infoCourse) {
        logger.info ( "接收到的查询参数：" + infoCourse.toString () );
        PageInfo<InfoCourse> pageInfo = courseService.findCourseList ( page, limit, infoCourse );
        PageResult<InfoCourse> result = new PageResult<> ();
        result.setData ( pageInfo.getList () );
        result.setCount ( pageInfo.getTotal () );
        return result;
    }

    /**
     * @return java.util.List<com.info.manage.entity.InfoCourse>
     * @Author xxy
     * @Description //TODO 查询所有的课程信息
     * @Date 2019/7/17 18:10
     * @Param [infoCourse]
     **/
    @RequestMapping(value = "findAllCourseList", method = RequestMethod.POST)
    public List<InfoCourse> findAllCourseList(InfoCourse infoCourse) {
        PageInfo<InfoCourse> pageInfo = courseService.findCourseList ( null, null, infoCourse );
        List<InfoCourse> infoCourseList = pageInfo.getList ();
        return infoCourseList;
    }


    /**
     * @return com.info.manage.util.PageResult<com.info.manage.entity.InfoCourse>
     * @Author xxy
     * @Description //TODO  查询所有课程
     * @Date 2019/7/12 16:42
     * @Param [infoCourse]
     **/
    @RequestMapping(value = "findAllClassList", method = RequestMethod.POST)
    public List<InfoCourse> findAllClassList(InfoCourse infoCourse) {
        PageInfo<InfoCourse> pageInfo = courseService.findCourseList ( null, null, infoCourse );
        List<InfoCourse> infoCourseList = pageInfo.getList ();
        return infoCourseList;
    }


    /**
     * @return com.info.manage.util.PageResult<java.lang.Object>
     * @Author xxy
     * @Description //TODO 保存，更新课程信息
     * @Date 2019/7/15 10:12
     * @Param [infoCourse, bindingResult]
     **/
    @RequestMapping(value = "saveAndUpdateCourse", method = RequestMethod.POST)
    public PageResult<Object> saveAndUpdateCourse(
            HttpServletRequest request,
            @Validated InfoCourse infoCourse,BindingResult bindingResult) {
        PageResult<Object> result = new PageResult<> ();
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        try {
            infoCourse.setCreaterId ( loginUser.getId () );
            infoCourse.setUpdaterId ( loginUser.getId () );
            courseService.saveAndUpdateCourse ( infoCourse );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "保存成功" );
        } catch (Exception e) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "保存失败" );
        }
        return result;
    }


    /**
     * @return com.info.manage.util.PageResult<java.lang.Object>
     * @Author xxy
     * @Description //TODO   单个删除
     * @Date 2019/7/11 11:07
     * @Param [id]
     **/
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public PageResult<Object> deleteById(Long id) {
        PageResult<Object> result = new PageResult<> ();
        try {
            courseService.deleteById ( id );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "删除成功" );
        } catch (Exception e) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "删除失败" );
        }
        return result;
    }

    /**
     * @return com.info.manage.util.PageResult<java.lang.Object>
     * @Author xxy
     * @Description 批量删除课程
     * @Date 2019/7/5 11:23
     * @Param [ids]
     **/
    @PostMapping(value = "/deleteCourseBatch")
    public PageResult<Object> deleteCourseBatch(@RequestParam(value = "ids[]", required = true) Long[] ids) {
        PageResult<Object> result = new PageResult<> ();
        try {
            logger.info ( "接收到的参数：" + ids );
            courseService.deleteCourseBatch ( ids );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "删除成功" );
        } catch (Exception e) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "删除失败" );
        }
        return result;
    }


    /**
     * @return com.info.manage.util.BussinessMsg
     * @Author xxy
     * @Description //TODO   批量导入课程信息
     * @Date 2019/7/10 9:38
     * @Param [file]
     **/
    @PostMapping(value = "/importCourseBatch")
    public BussinessMsg importCourseBatch(@RequestParam MultipartFile file, HttpServletRequest request) {
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        InputStream inputStream = null;
        BussinessMsg msg = null;
        try {
            inputStream = new BufferedInputStream ( file.getInputStream () );
            List<Object> data = new ArrayList<> ();
            ExcelListener excelListener = new ExcelListener ( data );
            Sheet sheet = new Sheet ( 1, 1, CourseImport.class );
            EasyExcelFactory.readBySax ( inputStream, sheet, excelListener );
            if (CollectionUtils.isEmpty ( data )) {
                msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.UPLOAD_ERROR_EMPTY );
                return msg;
            }
            for (Object object : data) {
                CourseImport courseImport = (CourseImport) object;
                InfoCourse infoCourse = new InfoCourse ();
                BeanUtils.copyProperties ( courseImport, infoCourse );
                infoCourse.setCreaterId ( loginUser.getId () );
                courseService.saveAndUpdateCourse ( infoCourse );
            }
            msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.GLOBAL_SUCCESS );
        } catch (Exception e) {
            msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.UPLOAD_ERROR );
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
        return msg;
    }

    /**
     * @return void
     * @Author xxy
     * @Description //TODO   下载导入课程信息的模板
     * @Date 2019/7/10 9:39
     * @Param [response]
     **/
    @RequestMapping(value = "downCourseTemplet", method = RequestMethod.POST)
    public void downCourseTemplet(HttpServletResponse response) {
        Workbook workbook = null;
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread ().getContextClassLoader ().getResourceAsStream ( "exceltemplet/course.xlsx" );
            if (inputStream == null) {
                logger.error ( "没找到对应的模板文件。。。。。。。。。。。" );
            }
            if (inputStream != null && inputStream.available () == 0) {
                logger.error ( "模板为空。。。。。。。。。" );
            }
            workbook = new XSSFWorkbook ( inputStream );//Excel2007
            //workbook = new HSSFWorkbook ( inputStream );03
            response.setContentType ( "application/binary;charset=ISO8859-1" );
            String fileName = java.net.URLEncoder.encode ( "批量导入课程模板", "UTF-8" );
            response.setHeader ( "Content-disposition", "attachment; filename=" + fileName + ".xlsx" );
            outputStream = response.getOutputStream ();
            workbook.write ( outputStream );
            outputStream.flush ();
        } catch (Exception e) {
            logger.error ( "下载失败。。。。。。。。" );
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
    }
}
