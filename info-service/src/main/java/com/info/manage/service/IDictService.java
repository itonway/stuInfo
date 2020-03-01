package com.info.manage.service;

import com.github.pagehelper.PageInfo;
import com.info.manage.entity.Dict;
import com.info.manage.entity.User;
import com.info.manage.form.DictForm;
import com.info.manage.form.UserForm;

import java.util.List;

public interface IDictService {
    List<Dict> findDictList(DictForm dictForm);

    Dict findDictByDictCode(String dictCode);
}
