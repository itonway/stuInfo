package com.info.manage.dao;

import com.info.manage.entity.DictItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictItemDao extends BaseDao<DictItem> {
    /**
     * @return java.util.List<com.info.manage.entity.DictItem>
     * @Author xxy
     * @Description //TODO  根据dict_code 查询所有的字典明细
     * @Date 2019/7/9 10:44
     * @Param [dictCode]
     **/
    List<DictItem> findDictItemListByDictCode(String dictCode);

    /**
     * @return com.info.manage.entity.DictItem
     * @Author xxy
     * @Description //TODO 根据 dict_code  ,item_code 查询字典明细
     * @Date 2019/7/9 10:44
     * @Param [dictCode, itemCode]
     **/
    DictItem findDictItemByDictCodeAndItemCode(@Param("dictCode") String dictCode, @Param("itemCode") String itemCode);

    /**
     * @return com.info.manage.entity.DictItem
     * @Author xxy
     * @Description //TODO 根据dict_code 和 item_name 得到 item_code
     * @Date 2019/7/10 17:37
     * @Param [dictCode, dictItemName]
     **/
    DictItem findDictItemCodeByItemName(@Param("dictCode") String dictCode, @Param("dictItemName") String dictItemName);
}
