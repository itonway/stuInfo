package com.info.manage.service;

import com.info.manage.entity.DictItem;

import java.util.List;

public interface IDictItemService {
    List<DictItem> findDictItemListByDictCode(String dictCode);

    /**
     * @return DictItem
     * @Author xxy
     * @Description //TODO 根据dict_code 和 item_name 得到 item_code
     * @Date 2019/7/10 17:34
     * @Param [dictCode, dictItemName]
     **/
    DictItem getDictItemCodeByItemName(String dictCode, String dictItemName);
}
