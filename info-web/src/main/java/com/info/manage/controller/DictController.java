package com.info.manage.controller;

import com.info.manage.entity.Dict;
import com.info.manage.entity.DictItem;
import com.info.manage.form.DictForm;
import com.info.manage.service.IDictItemService;
import com.info.manage.service.IDictService;
import com.info.manage.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author xxy
 * @Description //TODO 字典管理
 * @Date 2019/7/21 15:53
 **/
@RestController
@RequestMapping(value = "/dict")
public class DictController {
    private static  final Logger logger = LoggerFactory.getLogger( DictController.class);

    @Autowired
    IDictService dictService;
    @Autowired
    IDictItemService dictItemService;

    /**
     * @Author xxy
     * @Description //TODO 查询字典列表
     * @Date 2019/7/21 15:54
     * @Param [dictForm]
     * @return com.info.manage.util.PageResult<com.info.manage.entity.Dict>
     **/
    @RequestMapping(value = "findDictList",method = {RequestMethod.GET,RequestMethod.POST })
    public PageResult<Dict> findDictList(
            DictForm dictForm){
        List<Dict> dictList = dictService.findDictList ( dictForm );
        PageResult<Dict> result = new PageResult<> ();
        result.setData ( dictList );
        return result;
    }
    
    /**
     * @Author xxy
     * @Description //TODO  根据code 查询字典详情
     * @Date 2019/7/21 15:55
     * @Param [dictCode]
     * @return com.info.manage.util.PageResult<com.info.manage.entity.DictItem>
     **/
    @RequestMapping(value = "findDictItemListByDictCode",method = {RequestMethod.GET,RequestMethod.POST })
    public PageResult<DictItem> findDictItemListByDictCode(String dictCode){
        Dict dict = dictService.findDictByDictCode ( dictCode );
        if(dict == null){
            logger.error ( "字典表中不存在该code:"+dictCode );
            return null;
        }
        List<DictItem> dictItemList = dictItemService.findDictItemListByDictCode ( dictCode );
        PageResult<DictItem> result = new PageResult<> ();
        result.setData ( dictItemList );
        return result;
    }

}
