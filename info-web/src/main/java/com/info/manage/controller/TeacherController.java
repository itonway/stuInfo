package com.info.manage.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.info.manage.entity.DictItem;
import com.info.manage.entity.InfoTeacher;
import com.info.manage.entity.User;
import com.info.manage.filter.ExcelListener;
import com.info.manage.form.TeacherExport;
import com.info.manage.form.TeacherImport;
import com.info.manage.service.IDictItemService;
import com.info.manage.service.ITeacherService;
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
 * @Description //TODO 教师管理
 * @Date 2019/7/5 11:17
 **/
@RestController
@RequestMapping(value = "teacher")
@Validated
public class TeacherController {

    private static final Logger logger = LoggerFactory.getLogger ( TeacherController.class );

    @Autowired
    ITeacherService teacherService;
    @Autowired
    IDictItemService dictItemService;

    /**
     * @return org.springframework.web.servlet.ModelAndView
     * @Author xxy
     * @Description //TODO   跳转到教师页面
     * @Date 2019/7/11 11:08
     * @Param [modelAndView]
     **/
    @RequestMapping(value = "jumpTeacherList")
    public ModelAndView jumpTeacherList(ModelAndView modelAndView) {
        modelAndView.setViewName ( "teacherManage/teacherList" );
        return modelAndView;
    }

    /**
     * @return com.info.manage.util.PageResult<com.info.manage.entity.InfoTeacher>
     * @Author xxy
     * @Description //TODO   查询教师列表
     * @Date 2019/7/11 11:08
     * @Param [page, limit, infoTeacher]
     **/
    @RequestMapping(value = "findTeacherList", method = RequestMethod.POST)
    public PageResult<InfoTeacher> findTeacherList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            InfoTeacher infoTeacher) {
        logger.info ( "接收到的查询参数：" + infoTeacher.toString () );
        PageInfo<InfoTeacher> pageInfo = teacherService.findTeacherList ( page, limit, infoTeacher );
        PageResult<InfoTeacher> result = new PageResult<> ();
        result.setData ( pageInfo.getList () );
        result.setCount ( pageInfo.getTotal () );
        return result;
    }


    /**
     * @return com.info.manage.util.PageResult<com.info.manage.entity.InfoTeacher>
     * @Author xxy
     * @Description //TODO  查询所有教师
     * @Date 2019/7/12 16:42
     * @Param [infoTeacher]
     **/
    @RequestMapping(value = "findAllTeacherList", method = RequestMethod.POST)
    public List<InfoTeacher> findAllTeacherList(InfoTeacher infoTeacher) {
        PageInfo<InfoTeacher> pageInfo = teacherService.findTeacherList ( null, null, infoTeacher );
        List<InfoTeacher> infoTeacherList = pageInfo.getList ();
        return infoTeacherList;
    }


    /**
     * @return com.info.manage.util.PageResult<java.lang.Object>
     * @Author xxy
     * @Description //TODO
     * @Date 2019/7/11 11:06
     * @Param [infoTeacher, bindingResult]
     **/
    @RequestMapping(value = "saveAndUpdateTeacher", method = RequestMethod.POST)
    public PageResult<Object> saveAndUpdateTeacher( HttpServletRequest request,@Validated InfoTeacher infoTeacher, BindingResult bindingResult) {
        PageResult<Object> result = new PageResult<> ();
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        try {
            infoTeacher.setCreaterId ( loginUser.getId () );
            infoTeacher.setUpdaterId ( loginUser.getId () );
            teacherService.saveAndUpdateTeacher ( infoTeacher );
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
            teacherService.deleteById ( id );
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
     * @Description 批量删除教师
     * @Date 2019/7/5 11:23
     * @Param [ids]
     **/
    @PostMapping(value = "/deleteTeacherBatch")
    public PageResult<Object> deleteTeacherBatch(@RequestParam(value = "ids[]", required = true) Long[] ids) {
        PageResult<Object> result = new PageResult<> ();
        try {
            logger.info ( "接收到的参数：" + ids );
            teacherService.deleteTeacherBatch ( ids );
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
     * @Description //TODO   批量导入教师信息
     * @Date 2019/7/10 9:38
     * @Param [file]
     **/
    @PostMapping(value = "/importTeacherBatch")
    public BussinessMsg importTeacherBatch(@RequestParam MultipartFile file, HttpServletRequest request) {
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        InputStream inputStream = null;
        BussinessMsg msg = null;
        try {
            inputStream = new BufferedInputStream ( file.getInputStream () );
            List<Object> data = new ArrayList<> ();
            ExcelListener excelListener = new ExcelListener ( data );
            Sheet sheet = new Sheet ( 1, 1, TeacherImport.class );
            EasyExcelFactory.readBySax ( inputStream, sheet, excelListener );
            if (CollectionUtils.isEmpty ( data )) {
                msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.UPLOAD_ERROR_EMPTY );
                return msg;
            }
            for (Object object : data) {
                TeacherImport teacherImport = (TeacherImport) object;
                InfoTeacher infoTeacher = new InfoTeacher ();
                BeanUtils.copyProperties ( teacherImport, infoTeacher );
                DictItem dictItem = dictItemService.getDictItemCodeByItemName ( DictEnum.SEX.getValue (), infoTeacher.getTeacherSex () );
                infoTeacher.setTeacherSex ( dictItem == null ? "" : dictItem.getDictItemCode () );
                infoTeacher.setCreaterId ( loginUser.getId () );
                teacherService.saveAndUpdateTeacher ( infoTeacher );
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
     * @Description //TODO   批量导出教师信息
     * @Date 2019/7/10 9:39
     * @Param [request, response, infoStu]
     **/
    @RequestMapping(value = "exportTeacherBatch", method = RequestMethod.POST)
    public void exportTeacherBatch(
            HttpServletRequest request, HttpServletResponse response,
            InfoTeacher infoTeacher) {
        PageInfo<InfoTeacher> stuListPage = teacherService.findTeacherList ( null, null, infoTeacher );
        List<InfoTeacher> teacherList = stuListPage.getList ();
        List<TeacherExport> teacherExportList = getTeacherExportList ( teacherList );
        try {
            ServletOutputStream out = response.getOutputStream ();
            ExcelWriter writer = new ExcelWriter ( out, ExcelTypeEnum.XLSX, true );
            Sheet sheet1 = new Sheet ( 1, 0, TeacherExport.class );
            sheet1.setSheetName ( "教师列表" );
            writer.write ( teacherExportList, sheet1 );
            String fileName = new String ( ("教师列表" + Dates.getDateTime ( new Date (), Dates.DATAFORMAT_YYYYMMDDHHMMSS )).getBytes (), "UTF-8" ) + ".xlsx";
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
     * @return java.util.List<com.info.manage.form.TeacherExport>
     * @Author xxy
     * @Description //TODO   转换教师信息
     * @Date 2019/7/15 18:08
     * @Param [teacherList]
     **/
    private List<TeacherExport> getTeacherExportList(List<InfoTeacher> teacherList) {
        List<TeacherExport> teacherExportList = new ArrayList<> ();
        for (InfoTeacher infoTeacher : teacherList) {
            TeacherExport teacherExport = new TeacherExport ();
            BeanUtils.copyProperties ( infoTeacher, teacherExport );
            teacherExportList.add ( teacherExport );
        }
        return teacherExportList;
    }


    /**
     * @return void
     * @Author xxy
     * @Description //TODO   下载导入学生信息的模板
     * @Date 2019/7/10 9:39
     * @Param [response]
     **/
    @RequestMapping(value = "downTeacherTemplet", method = RequestMethod.POST)
    public void downTeacherTemplet(HttpServletResponse response) {
        Workbook workbook = null;
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread ().getContextClassLoader ().getResourceAsStream ( "exceltemplet/teacher.xlsx" );
            if (inputStream == null) {
                logger.error ( "没找到对应的模板文件。。。。。。。。。。。" );
            }
            if (inputStream != null && inputStream.available () == 0) {
                logger.error ( "模板为空。。。。。。。。。" );
            }
            workbook = new XSSFWorkbook ( inputStream );//Excel2007
            //workbook = new HSSFWorkbook ( inputStream );03
            response.setContentType ( "application/binary;charset=ISO8859-1" );
            String fileName = java.net.URLEncoder.encode ( "批量导入教师模板", "UTF-8" );
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
