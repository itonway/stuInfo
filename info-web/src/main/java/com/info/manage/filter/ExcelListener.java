package com.info.manage.filter;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxy
 * @ClassName ExcelListener
 * @Description todo
 * @Date 2019/3/13 10:26
 **/
public class ExcelListener extends AnalysisEventListener {

    private List<Object> data = new ArrayList<> ();

    public ExcelListener() {

    }

    public ExcelListener(List<Object> data) {
        this.data = data;
    }


    @Override
    public void invoke(Object object, AnalysisContext analysisContext) {
        data.add ( object );
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

}
