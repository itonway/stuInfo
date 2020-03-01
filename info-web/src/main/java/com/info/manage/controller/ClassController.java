package com.info.manage.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.info.manage.entity.InfoClass;
import com.info.manage.entity.InfoStu;
import com.info.manage.entity.User;
import com.info.manage.filter.ExcelListener;
import com.info.manage.form.ClassExport;
import com.info.manage.form.ClassImport;
import com.info.manage.service.IClassService;
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
import java.util.Date;
import java.util.List;

/**
 * @Author xxy
 * @Description //TODO 班级管理
 * @Date 2019/7/5 11:17
 **/
@RestController
@RequestMapping(value = "class")
@Validated
public class ClassController {

    private static final Logger logger = LoggerFactory.getLogger ( ClassController.class );

    @Autowired
    IClassService classService;
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
    @RequestMapping(value = "/jumpClassList")
    public ModelAndView jumpUserList(ModelAndView modelAndView) {
        modelAndView.setViewName ( "classManage/classList" );
        return modelAndView;
    }

    /**
     * @return com.info.manage.util.PageResult<com.info.manage.entity.InfoClass>
     * @Author xxy
     * @Description //TODO   查询班级列表
     * @Date 2019/7/11 11:08
     * @Param [page, limit, infoClass]
     **/
    @RequestMapping(value = "findClassList", method = RequestMethod.POST)
    public PageResult<InfoClass> findClassList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            InfoClass infoClass) {
        logger.info ( "接收到的查询参数：" + infoClass.toString () );
        PageInfo<InfoClass> pageInfo = classService.findClassList ( page, limit, infoClass );
        PageResult<InfoClass> result = new PageResult<> ();
        result.setData ( pageInfo.getList () );
        result.setCount ( pageInfo.getTotal () );
        return result;
    }


    /**
     * @return com.info.manage.util.PageResult<com.info.manage.entity.InfoClass>
     * @Author xxy
     * @Description //TODO  查询所有班级
     * @Date 2019/7/12 16:42
     * @Param [infoClass]
     **/
    @RequestMapping(value = "findAllClassList", method = RequestMethod.POST)
    public List<InfoClass> findAllClassList(InfoClass infoClass) {
        PageInfo<InfoClass> pageInfo = classService.findClassList ( null, null, infoClass );
        List<InfoClass> infoClassList = pageInfo.getList ();
        return infoClassList;
    }


    /**
     * @return com.info.manage.util.PageResult<java.lang.Object>
     * @Author xxy
     * @Description //TODO
     * @Date 2019/7/11 11:06
     * @Param [infoClass, bindingResult]
     **/
    @RequestMapping(value = "saveAndUpdateClass", method = RequestMethod.POST)
    public PageResult<Object> saveAndUpdateClass( HttpServletRequest request,@Validated InfoClass infoClass, BindingResult bindingResult) {
        PageResult<Object> result = new PageResult<> ();
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        try {
            infoClass.setCreaterId ( loginUser.getId () );
            infoClass.setUpdaterId ( loginUser.getId () );
            classService.saveAndUpdateClass ( infoClass );
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
     * @Description //TODO   单个删除  同时删除班级下面的学生和成绩
     * @Date 2019/7/11 11:07
     * @Param [id]
     **/
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public PageResult<Object> deleteById(Long id) {
        PageResult<Object> result = new PageResult<> ();
        try {
            classService.deleteById ( id );
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
     * @Description 批量删除班级 同时删除班级下面的学生和成绩
     * @Date 2019/7/5 11:23
     * @Param [ids]
     **/
    @PostMapping(value = "/deleteClassBatch")
    public PageResult<Object> deleteClassBatch(@RequestParam(value = "ids[]", required = true) Long[] ids) {
        PageResult<Object> result = new PageResult<> ();
        try {
            classService.deleteClassBatch ( ids );
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
     * @Description //TODO   批量导入学生信息
     * @Date 2019/7/10 9:38
     * @Param [file]
     **/
    @PostMapping(value = "/importClassBatch")
    public BussinessMsg importClassBatch(@RequestParam MultipartFile file, HttpServletRequest request) {
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        InputStream inputStream = null;
        BussinessMsg msg = null;
        try {
            inputStream = new BufferedInputStream ( file.getInputStream () );
            List<Object> data = new ArrayList<> ();
            ExcelListener excelListener = new ExcelListener ( data );
            Sheet sheet = new Sheet ( 1, 1, ClassImport.class );
            EasyExcelFactory.readBySax ( inputStream, sheet, excelListener );
            if (CollectionUtils.isEmpty ( data )) {
                msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.UPLOAD_ERROR_EMPTY );
                return msg;
            }
            for (Object object : data) {
                ClassImport classImport = (ClassImport) object;
                InfoClass infoClass = new InfoClass ();
                BeanUtils.copyProperties ( classImport, infoClass );
                infoClass.setCreaterId ( loginUser.getId () );
                classService.saveAndUpdateClass ( infoClass );
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
     * @Description //TODO   批量导出学生信息
     * @Date 2019/7/10 9:39
     * @Param [request, response, infoStu]
     **/
    @RequestMapping(value = "exportClassBatch", method = RequestMethod.POST)
    public void exportClassBatch(
            HttpServletRequest request, HttpServletResponse response,
            InfoClass infoClass) {
        PageInfo<InfoClass> stuListPage = classService.findClassList ( null, null, infoClass );
        List<InfoClass> classList = stuListPage.getList ();
        List<ClassExport> classExportList = getClassExportList ( classList );
        try {
            ServletOutputStream out = response.getOutputStream ();
            ExcelWriter writer = new ExcelWriter ( out, ExcelTypeEnum.XLSX, true );
            Sheet sheet1 = new Sheet ( 1, 0, ClassExport.class );
            sheet1.setSheetName ( "班级列表" );
            writer.write ( classExportList, sheet1 );
            String fileName = new String ( ("班级列表" + Dates.getDateTime ( new Date (), Dates.DATAFORMAT_YYYYMMDDHHMMSS )).getBytes (), "UTF-8" ) + ".xlsx";
            String header = request.getHeader ( "User-Agent" );
            fileName = FileHandlerUtil.getFileName ( fileName, header );
            response.setCharacterEncoding ( "UTF-8" );
            response.setContentType ( "application/vnd.ms-excel" );
            response.setHeader ( "Content-disposition", "attachment;filename=" + fileName );
            writer.finish ();
            out.flush ();
            out.close ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


    /**
     * @return java.util.List<com.info.manage.form.StuExport>
     * @Author xxy
     * @Description //TODO   得到要导出的学生信息
     * @Date 2019/7/10 9:37
     * @Param [infoStuList]
     **/
    public List<ClassExport> getClassExportList(List<InfoClass> infoClassList) {
        List<ClassExport> classExportList = new ArrayList<> ();
        for (InfoClass infoClass : infoClassList) {
            ClassExport classExport = new ClassExport ();
            BeanUtils.copyProperties ( infoClass, classExport );
            classExportList.add ( classExport );
        }
        return classExportList;
    }


    /**
     * @return void
     * @Author xxy
     * @Description //TODO   下载导入学生信息的模板
     * @Date 2019/7/10 9:39
     * @Param [response]
     **/
    @RequestMapping(value = "downClassTemplet", method = RequestMethod.POST)
    public void downClassTemplet(HttpServletResponse response) {
        Workbook workbook = null;
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread ().getContextClassLoader ().getResourceAsStream ( "exceltemplet/class.xlsx" );
            if (inputStream == null) {
                logger.error ( "没找到对应的模板文件。。。。。。。。。。。" );
            }
            if (inputStream != null && inputStream.available () == 0) {
                logger.error ( "模板为空。。。。。。。。。" );
            }
            workbook = new XSSFWorkbook ( inputStream );//Excel2007
            //workbook = new HSSFWorkbook ( inputStream );03
            response.setContentType ( "application/binary;charset=ISO8859-1" );
            String fileName = java.net.URLEncoder.encode ( "批量导入班级模板", "UTF-8" );
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

    /**
     * @return java.util.List<com.info.manage.entity.InfoStu>
     * @Author xxy
     * @Description //TODO
     * @Date 2019/7/12 16:03
     * @Param [infoStu]
     **/
    @RequestMapping(value = "findStuListAssignClass", method = {RequestMethod.POST, RequestMethod.GET})
    public List<InfoStu> findStuListAssignClass(Long assignClassId) {
        logger.info ( "接收到的查询参数：" + assignClassId );
        InfoStu infoStu = new InfoStu ();
        infoStu.setAssignClassId ( assignClassId );
        PageInfo<InfoStu> pageInfo = stuService.findStuList ( null, null, infoStu );
        List<InfoStu> infoList = pageInfo.getList ();
        return infoList;
    }

    /**
     * @return com.info.manage.util.PageResult<java.lang.Object>
     * @Author xxy
     * @Description //TODO   保存分配完班级的学生
     * @Date 2019/7/12 16:13
     * @Param [infoStu]
     **/
    @PostMapping(value = "saveAssignedStu")
    public PageResult<Object> saveAssignedStu(String infoStuListStr, Long classId, String classNo) {
        PageResult<Object> result = new PageResult<> ();
        try {
            List<InfoStu> infoStuList = JSON.parseArray ( infoStuListStr, InfoStu.class );
            for (InfoStu infoStu : infoStuList) {
                infoStu.setClassId ( classId );
                infoStu.setClassNo ( classNo );
                stuService.saveAndUpdateStu ( infoStu );
            }
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "保存成功" );
        } catch (Exception e) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "保存失败" );
        }
        return result;
    }


}
