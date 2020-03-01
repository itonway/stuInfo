package com.info.manage.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.info.manage.entity.InfoScore;
import com.info.manage.entity.User;
import com.info.manage.filter.ExcelListener;
import com.info.manage.form.ScoreExport;
import com.info.manage.form.ScoreImport;
import com.info.manage.service.IDictItemService;
import com.info.manage.service.IScoreService;
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
 * @Description //TODO 分数管理
 * @Date 2019/7/5 11:17
 **/
@RestController
@RequestMapping(value = "score")
public class ScoreController {

    private static final Logger logger = LoggerFactory.getLogger ( ScoreController.class );

    @Autowired
    IScoreService scoreService;

    @Autowired
    IDictItemService dictItemService;


    /**
     * @return org.springframework.web.servlet.ModelAndView
     * @Author xxy
     * @Description //TODO
     * @Date 2019/7/5 11:18
     * @Param [modelAndView]
     **/
    @RequestMapping(value = "/jumpScoreList")
    public ModelAndView jumpScoreList(ModelAndView modelAndView) {
        modelAndView.setViewName ( "scoreManage/scoreList" );
        return modelAndView;
    }

    /**
     * @return com.info.manage.util.PageResult<com.info.manage.entity.InfoScore>
     * @Author xxy
     * @Description //TODO   查询成绩列表
     * @Date 2019/7/17 17:32
     * @Param [page, limit, infoScore]
     **/
    @RequestMapping(value = "findScoreList", method = RequestMethod.POST)
    public PageResult<InfoScore> findScoreList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            InfoScore infoScore) {
        logger.info ( "接收到的查询参数：" + infoScore.toString () );
        PageInfo<InfoScore> pageInfo = scoreService.findScoreList ( page, limit, infoScore );
        PageResult<InfoScore> result = new PageResult<> ();
        result.setData ( pageInfo.getList () );
        result.setCount ( pageInfo.getTotal () );
        return result;
    }

    /**
     * @return com.info.manage.util.PageResult<java.lang.Object>
     * @Author xxy
     * @Description //TODO   ControllerValidatorAspect 进入切面
     * @Date 2019/7/11 11:39
     * @Param [infoStu, bindingResult]
     **/
    @RequestMapping(value = "saveAndUpdateScore", method = RequestMethod.POST)
    public PageResult<Object> saveAndUpdateScore( HttpServletRequest request,@Validated InfoScore infoScore, BindingResult bindingResult) {
        PageResult<Object> result = new PageResult<> ();
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        try {
            infoScore.setCreaterId ( loginUser.getId () );
            infoScore.setUpdaterId ( loginUser.getId () );
            scoreService.saveAndUpdateScore ( infoScore );
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
     * @Description //TODO  单个删除
     * @Date 2019/7/17 17:33
     * @Param [id]
     **/
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public PageResult<Object> deleteById(Long id) {
        PageResult<Object> result = new PageResult<> ();
        try {
            scoreService.deleteById ( id );
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
     * @Description 批量删除成绩
     * @Date 2019/7/5 11:23
     * @Param [ids]
     **/
    @PostMapping(value = "/deleteScoreBatch")
    public PageResult<Object> deleteScoreBatch(@RequestParam(value = "ids[]", required = true) Long[] ids) {
        PageResult<Object> result = new PageResult<> ();
        try {
            logger.info ( "接收到的参数：" + ids );
            scoreService.deleteScoreBatch ( ids );
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
     * @Description //TODO 批量导入学生信息
     * @Date 2019/7/17 21:26
     * @Param [file]
     **/
    @PostMapping(value = "importScoreBatch")
    public BussinessMsg importScoreBatch(@RequestParam MultipartFile file, HttpServletRequest request) {
        User loginUser = (User) request.getSession ().getAttribute ( "loginUser" );
        InputStream inputStream = null;
        BussinessMsg msg = null;
        try {
            inputStream = new BufferedInputStream ( file.getInputStream () );
            List<Object> data = new ArrayList<> ();
            ExcelListener excelListener = new ExcelListener ( data );
            Sheet sheet = new Sheet ( 1, 1, ScoreImport.class );
            EasyExcelFactory.readBySax ( inputStream, sheet, excelListener );
            if (CollectionUtils.isEmpty ( data )) {
                msg = BussinessMsgUtil.returnCodeMessage ( BussinessCode.UPLOAD_ERROR_EMPTY );
                return msg;
            }
            for (Object object : data) {
                ScoreImport scoreImport = (ScoreImport) object;
                InfoScore infoScore = new InfoScore ();
                BeanUtils.copyProperties ( scoreImport, infoScore );
                infoScore.setCreaterId ( loginUser.getId () );
                scoreService.saveAndUpdateScore ( infoScore );
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
     * @Description //TODO 批量导出学生成绩信息
     * @Date 2019/7/17 21:25
     * @Param [request, response, infoScore]
     **/
    @RequestMapping(value = "exportScoreBatch", method = RequestMethod.POST)
    public void exportScoreBatch(
            HttpServletRequest request, HttpServletResponse response,
            InfoScore infoScore) {
        PageInfo<InfoScore> scorePageInfo = scoreService.findScoreList ( null, null, infoScore );
        List<InfoScore> infoStuList = scorePageInfo.getList ();
        List<ScoreExport> scoreExportList = getScoreExportList ( infoStuList );
        try {
            ServletOutputStream out = response.getOutputStream ();
            ExcelWriter writer = new ExcelWriter ( out, ExcelTypeEnum.XLSX, true );
            Sheet sheet1 = new Sheet ( 1, 0, ScoreExport.class );
            sheet1.setSheetName ( "学生成绩列表" );
            writer.write ( scoreExportList, sheet1 );
            String fileName = new String ( ("学生成绩列表" + Dates.getDateTime ( new Date (), Dates.DATAFORMAT_YYYYMMDDHHMMSS )).getBytes (), "UTF-8" ) + ".xlsx";
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
     * @return java.util.List<com.info.manage.form.ScoreExport>
     * @Author xxy
     * @Description //TODO   得到要导出的成绩信息
     * @Date 2019/7/10 9:37
     * @Param [infoStuList]
     **/
    public List<ScoreExport> getScoreExportList(List<InfoScore> infoStuList) {
        List<ScoreExport> scoreExportList = new ArrayList<> ();
        for (InfoScore score : infoStuList) {
            ScoreExport scoreExport = new ScoreExport ();
            BeanUtils.copyProperties ( score, scoreExport );
            scoreExportList.add ( scoreExport );
        }
        return scoreExportList;
    }


    /**
     * @return void
     * @Author xxy
     * @Description //TODO   下载导入学生信息的模板
     * @Date 2019/7/10 9:39
     * @Param [response]
     **/
    @RequestMapping(value = "downScoreTemplet", method = RequestMethod.POST)
    public void downScoreTemplet(HttpServletResponse response) {
        Workbook workbook = null;
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread ().getContextClassLoader ().getResourceAsStream ( "exceltemplet/score.xlsx" );
            if (inputStream == null) {
                logger.error ( "没找到对应的模板文件。。。。。。。。。。。" );
            }
            if (inputStream != null && inputStream.available () == 0) {
                logger.error ( "模板为空。。。。。。。。。" );
            }
            workbook = new XSSFWorkbook ( inputStream );
            //workbook = new HSSFWorkbook ( inputStream );
            response.setContentType ( "application/binary;charset=ISO8859-1" );
            String fileName = java.net.URLEncoder.encode ( "批量导入学生成绩模板", "UTF-8" );
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
