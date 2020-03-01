package com.info.manage.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.info.manage.entity.DictItem;
import com.info.manage.entity.InfoStu;
import com.info.manage.entity.User;
import com.info.manage.filter.ExcelListener;
import com.info.manage.form.StuExport;
import com.info.manage.form.StuImport;
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
 * @Description //TODO 学生管理
 * @Date 2019/7/5 11:17
 **/
@RestController
@RequestMapping(value = "stu")
@Validated
public class StuController {
    //ctrl shift F9
    //https://blog.csdn.net/hzr0523/article/details/85053062  SpringBoot+Freemarker+Layui
    private static final Logger logger = LoggerFactory.getLogger ( StuController.class );

    @Autowired
    IStuService stuService;

    @Autowired
    IDictItemService dictItemService;


    /**
     * @return org.springframework.web.servlet.ModelAndView
     * @Author xxy
     * @Description //TODO
     * @Date 2019/7/5 11:18
     * @Param [modelAndView]
     **/
    @RequestMapping(value = "/jumpStuList")
    public ModelAndView jumpUserList(ModelAndView modelAndView) {
        modelAndView.setViewName ( "stuManage/stuList" );
        return modelAndView;
    }

    /**
     * @Author xxy
     * @Description //TODO   分页查询学生信息
     * @Date 2019/7/17 18:07 
     * @Param [page, limit, infoStu]
     * @return com.info.manage.util.PageResult<com.info.manage.entity.InfoStu>
     **/
    @RequestMapping(value = "findStuList", method = RequestMethod.POST)
    public PageResult<InfoStu> findStuList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            InfoStu infoStu) {
        logger.info ( "接收到的查询参数：" + infoStu.toString () );
        PageInfo<InfoStu> pageInfo = stuService.findStuList ( page, limit, infoStu );
        PageResult<InfoStu> result = new PageResult<> ();
        result.setData ( pageInfo.getList () );
        result.setCount ( pageInfo.getTotal () );
        return result;
    }


    /**
     * @return java.util.List<com.info.manage.entity.InfoStu>
     * @Author xxy
     * @Description //TODO  查询所有的学生信息
     * @Date 2019/7/17 18:09
     * @Param [infoStu]
     **/
    @RequestMapping(value = "findAllStuList", method = RequestMethod.POST)
    public List<InfoStu> findAllStuList(InfoStu infoStu) {
        logger.info ( "接收到的查询参数：" + infoStu.toString () );
        PageInfo<InfoStu> pageInfo = stuService.findStuList ( null, null, infoStu );
        List<InfoStu> infoList = pageInfo.getList ();
        return infoList;
    }

    /**
     * @Author xxy
     * @Description //TODO   ControllerValidatorAspect 进入切面
     * @Date 2019/7/11 11:39 
     * @Param [infoStu, bindingResult]
     * @return com.info.manage.util.PageResult<java.lang.Object>
     **/
    @RequestMapping(value = "saveAndUpdateStu", method = RequestMethod.POST)
    public PageResult<Object> saveAndUpdateStu( HttpServletRequest request,@Validated InfoStu infoStu, BindingResult bindingResult) {
        PageResult<Object> result = new PageResult<> ();
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        try {
            infoStu.setCreaterId ( loginUser.getId () );
            infoStu.setUpdaterId ( loginUser.getId () );
            stuService.saveAndUpdateStu ( infoStu );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "保存成功" );
        } catch (Exception e) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "保存失败" );
        }
        return result;
    }


    /**
     * @Author xxy
     * @Description //TODO  根据id查询数据
     * @Date 2019/7/18 10:18
     * @Param [id]
     * @return com.info.manage.entity.InfoStu
     **/
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public InfoStu findById(Long id) {
        InfoStu infoStu = stuService.findById ( id );
        return infoStu;
    }

    /**
     * @Author xxy
     * @Description //TODO   删除学生同时删除学习成绩
     * @Date 2019/7/18 10:17 
     * @Param [id]
     * @return com.info.manage.util.PageResult<java.lang.Object>
     **/
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public PageResult<Object> deleteById(Long id) {
        PageResult<Object> result = new PageResult<> ();
        try {
            stuService.deleteById ( id );
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
     * @Description //TODO  删除学生同时删除学习成绩
     * @Date 2019/7/18 10:17
     * @Param [ids]
     **/
    @PostMapping(value = "/deleteStuBatch")
    public PageResult<Object> deleteStuBatch(@RequestParam(value = "ids[]", required = true) Long[] ids) {
        PageResult<Object> result = new PageResult<> ();
        try {
            logger.info ( "接收到的参数：" + ids );
            stuService.deleteStuBatch ( ids );
            result.setCode ( Const.SUCCESSCODE );
            result.setMessage ( "删除成功" );
        } catch (Exception e) {
            result.setCode ( Const.FAILCODE );
            result.setMessage ( "删除失败" );
        }
        return result;
    }


    /**
     * @Author xxy
     * @Description //TODO 批量导入学生信息
     * @Date 2019/7/18 10:17
     * @Param [file]
     * @return com.info.manage.util.BussinessMsg
     **/
    @PostMapping(value = "importStuBatch")
    public BussinessMsg importStuBatch(@RequestParam MultipartFile file, HttpServletRequest request) {
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        InputStream inputStream = null;
        BussinessMsg msg = null;
        try {
            inputStream = new BufferedInputStream ( file.getInputStream () );
            List<Object> data = new ArrayList<> ();
            ExcelListener excelListener = new ExcelListener ( data );
            Sheet sheet = new Sheet ( 1, 1, StuImport.class );
            EasyExcelFactory.readBySax ( inputStream, sheet, excelListener );
            if (CollectionUtils.isEmpty ( data )) {
                msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.UPLOAD_ERROR_EMPTY );
                return msg;
            }
            for (Object object : data) {
                StuImport stuImport = (StuImport) object;
                InfoStu infoStu = new InfoStu ();
                BeanUtils.copyProperties ( stuImport, infoStu );
                DictItem dictItem = dictItemService.getDictItemCodeByItemName ( DictEnum.SEX.getValue (), infoStu.getStuSex () );
                infoStu.setStuSex ( dictItem == null ? "" : dictItem.getDictItemCode () );
                infoStu.setCreaterId ( loginUser.getId () );
                stuService.saveAndUpdateStu ( infoStu );
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
    @RequestMapping(value = "exportStuBatch", method = RequestMethod.POST)
    public void exportStuBatch(
            HttpServletRequest request, HttpServletResponse response,
            InfoStu infoStu) {
        PageInfo<InfoStu> stuListPage = stuService.findStuList ( null, null, infoStu );
        List<InfoStu> infoStuList = stuListPage.getList ();
        List<StuExport> stuExportList = getStuExportList ( infoStuList );
        try {
            ServletOutputStream out = response.getOutputStream ();
            ExcelWriter writer = new ExcelWriter ( out, ExcelTypeEnum.XLSX, true );
            Sheet sheet1 = new Sheet ( 1, 0, StuExport.class );
            sheet1.setSheetName ( "学生信息列表" );
            writer.write ( stuExportList, sheet1 );
            String fileName = new String ( ("学生信息列表" + Dates.getDateTime ( new Date (), Dates.DATAFORMAT_YYYYMMDDHHMMSS )).getBytes (), "UTF-8" ) + ".xlsx";
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
    public List<StuExport> getStuExportList(List<InfoStu> infoStuList) {
        List<StuExport> stuExportList = new ArrayList<> ();
        for (InfoStu infoStu : infoStuList) {
            StuExport stuExport = new StuExport ();
            BeanUtils.copyProperties ( infoStu, stuExport );
            stuExportList.add ( stuExport );
        }
        return stuExportList;
    }


    /**
     * @return void
     * @Author xxy
     * @Description //TODO   下载导入学生信息的模板
     * @Date 2019/7/10 9:39
     * @Param [response]
     **/
    @RequestMapping(value = "downStuTemplet", method = RequestMethod.POST)
    public void downStuTemplet(HttpServletResponse response) {
        Workbook workbook = null;
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread ().getContextClassLoader ().getResourceAsStream ( "exceltemplet/stu.xlsx" );
            if (inputStream == null) {
                logger.error ( "没找到对应的模板文件。。。。。。。。。。。" );
            }
            if (inputStream != null && inputStream.available () == 0) {
                logger.error ( "模板为空。。。。。。。。。" );
            }
            workbook = new XSSFWorkbook ( inputStream );
            //workbook = new HSSFWorkbook ( inputStream );
            response.setContentType ( "application/binary;charset=ISO8859-1" );
            String fileName = java.net.URLEncoder.encode ( "批量导入学生模板", "UTF-8" );
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
